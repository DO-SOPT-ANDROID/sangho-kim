package org.sopt.dosopttemplate.presentation.auth

import android.os.Bundle
import androidx.activity.viewModels
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivitySignUpBinding
import org.sopt.dosopttemplate.domain.entity.User
import org.sopt.dosopttemplate.presentation.auth.LoginActivity.Companion.EXTRA_USER
import org.sopt.dosopttemplate.util.base.BindingActivity
import org.sopt.dosopttemplate.util.extension.setOnSingleClickListener
import org.sopt.dosopttemplate.util.extension.toast

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel
        initSignUpBtnListener()
        observeSignUpFormat()
        observeSignUpResult()
    }

    private fun initSignUpBtnListener() {
        binding.btnSignUp.setOnSingleClickListener {
            viewModel.postSignUpToServer()
        }
    }

    private fun observeSignUpFormat() {
        viewModel.isIdValid.observe(this) { isIdValid ->
            //binding.layoutSignUpId.error = if (isIdValid) "" else "아이디 형식이 올바르지 않습니다."
            viewModel.checkButtonValid()
        }
        viewModel.isPwValid.observe(this) { isPwValid ->
            //binding.layoutSignUpPw.error = if (isPwValid) "" else "비밀번호 형식이 올바르지 않습니다."
            viewModel.checkButtonValid()
        }
    }

    private fun observeSignUpResult() {
        viewModel.signUpState.observe(this) { state ->
            when (state) {
                is ServerState.Success -> {
                    toast(getString(R.string.sign_in_success))
                    returnToLoginActivity(state.data)
                }

                is ServerState.Failure -> toast(getString(R.string.sign_in_failure))

                is ServerState.ServerError -> toast(getString(R.string.server_error))

                is ServerState.Empty -> return@observe
            }
        }
    }

    private fun returnToLoginActivity(user: User) {
        intent.apply {
            putExtra(EXTRA_USER, user)
            setResult(RESULT_OK, this)
        }
        finish()
    }
}