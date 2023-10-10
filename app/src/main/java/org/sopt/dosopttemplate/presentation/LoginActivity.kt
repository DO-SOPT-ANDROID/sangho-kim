package org.sopt.dosopttemplate.presentation

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.util.base.BindingActivity
import org.sopt.dosopttemplate.util.view.setOnSingleClickListener
import snackBar

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private lateinit var signedUser: User
    private lateinit var signUpActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSignUpActivityLauncher()
        initSignUpBtnListener()
        initLoginBtnListener()
    }

    private fun setSignUpActivityLauncher() {
        signUpActivityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                signedUser = if (Build.VERSION.SDK_INT >= TIRAMISU) {
                    result.data?.getParcelableExtra(EXTRA_USER, User::class.java) ?: return@registerForActivityResult
                } else {
                    result.data?.getParcelableExtra(EXTRA_USER) ?: return@registerForActivityResult
                }
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
                snackBar(binding.root.rootView) { "회원가입을 진행해주세요." }
            } else if (!checkLoginAvailable(signedUser)) {
                snackBar(binding.root.rootView) { "아이디 혹은 비밀번호가 잘못되었습니다." }
            } else {
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

    companion object {
        const val EXTRA_USER = "USER"
    }
}