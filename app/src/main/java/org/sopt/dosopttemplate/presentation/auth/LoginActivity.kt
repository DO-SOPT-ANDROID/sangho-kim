package org.sopt.dosopttemplate.presentation.auth

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.presentation.main.MainActivity
import org.sopt.dosopttemplate.util.base.BindingActivity
import org.sopt.dosopttemplate.util.intent.getParcelable
import org.sopt.dosopttemplate.util.view.setOnSingleClickListener
import snackBar
import toast

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private lateinit var signedUser: User
    private lateinit var signUpActivityLauncher: ActivityResultLauncher<Intent>

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSignUpActivityLauncher()
        initSignUpBtnListener()
        initLoginBtnListener()
        initOnBackPressedListener()
    }

    private fun setSignUpActivityLauncher() {
        signUpActivityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                signedUser = result.data?.getParcelable(EXTRA_USER, User::class.java)
                    ?: return@registerForActivityResult
            }
        }
    }

    private fun initSignUpBtnListener() {
        binding.btnSignUp.setOnSingleClickListener {
            Intent(this, SignUpActivity::class.java).apply {
                signUpActivityLauncher.launch(this)
            }
        }
    }

    private fun initLoginBtnListener() {
        binding.btnLogin.setOnSingleClickListener {
            if (!::signedUser.isInitialized) {
                snackBar(binding.root) { "회원가입을 진행해주세요." }
            } else if (!checkLoginAvailable(signedUser)) {
                snackBar(binding.root) { "아이디 혹은 비밀번호가 잘못되었습니다." }
            } else {
                toast("로그인에 성공했습니다.")
                startMainActivity()
            }
        }
    }

    private fun checkLoginAvailable(signedUser: User): Boolean {
        return signedUser.id == binding.etLoginId.text.toString() && signedUser.pw == binding.etLoginPw.text.toString()
    }

    private fun startMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            putExtra(EXTRA_USER, signedUser)
            addFlags(FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
        finish()
    }

    private fun initOnBackPressedListener() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backPressedTime >= BACK_INTERVAL) {
                    backPressedTime = System.currentTimeMillis()
                    toast("버튼을 한번 더 누르면 종료됩니다.")
                } else {
                    finish()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    companion object {
        const val EXTRA_USER = "USER"
        const val BACK_INTERVAL = 2000
    }
}