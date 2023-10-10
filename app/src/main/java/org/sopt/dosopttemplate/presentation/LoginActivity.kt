package org.sopt.dosopttemplate.presentation

import android.app.Activity
import android.content.Intent
import android.os.Build
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

        initSignUpBtnListener()
        initLoginBtnListener()
        setSignUpActivityLauncher()
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
            val editedId = binding.etLoginId.text.toString()
            val editedPw = binding.etLoginPw.text.toString()
            if (::signedUser.isInitialized) {
                if (signedUser.id == editedId && signedUser.pw == editedPw) {
                    Intent(this, MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(this)
                    }
                    finish()
                } else {
                    snackBar(binding.root.rootView) { "아이디 혹은 비밀번호가 잘못되었습니다." }
                }
            } else {
                snackBar(binding.root.rootView) { "회원가입을 진행해주세요." }
            }
        }
    }

    private fun setSignUpActivityLauncher() {
        signUpActivityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null) {
                    signedUser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(EXTRA_USER, User::class.java)
                            ?: return@registerForActivityResult
                    } else {
                        intent.getParcelableExtra(EXTRA_USER) ?: return@registerForActivityResult
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_USER = "USER"
    }
}