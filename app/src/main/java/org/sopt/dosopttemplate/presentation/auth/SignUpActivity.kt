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

        initSignUpBtnListener()
        observeSignUpResult()
    }

    private fun initSignUpBtnListener() {
        binding.btnSignUp.setOnSingleClickListener {
            viewModel.postSignUpToServer()
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