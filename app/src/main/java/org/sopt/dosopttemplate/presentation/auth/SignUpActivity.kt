package org.sopt.dosopttemplate.presentation.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        observeIdFormat()
        observePwFormat()
        observeSignUpResult()
    }

    private fun initSignUpBtnListener() {
        binding.btnSignUp.setOnSingleClickListener {
            viewModel.postSignUpToServer()
        }
    }

    private fun observeIdFormat() {
        viewModel.isIdValid.observe(this) { isIdValid ->
            if (!isIdValid && !viewModel.idText.value.isNullOrBlank()) {
                binding.layoutSignUpId.isErrorEnabled = true
                binding.layoutSignUpId.error = getString(R.string.sign_up_id_error)
            } else {
                binding.layoutSignUpId.isErrorEnabled = false
            }
            viewModel.checkButtonValid()
        }
    }

    private fun observePwFormat() {
        viewModel.isPwValid.observe(this) { isPwValid ->
            if (!isPwValid && !viewModel.idText.value.isNullOrBlank()) {
                binding.layoutSignUpPw.isErrorEnabled = true
                binding.layoutSignUpPw.error = getString(R.string.sign_up_pw_error)
            } else {
                binding.layoutSignUpPw.isErrorEnabled = false
            }
            viewModel.checkButtonValid()
        }
    }

    private fun observeSignUpResult() {
        viewModel.signUpState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is ServerState.Success -> {
                    toast(getString(R.string.sign_in_success))
                    returnToLoginActivity(state.data)
                }

                is ServerState.Failure -> toast(getString(R.string.sign_in_failure))

                is ServerState.ServerError -> toast(getString(R.string.server_error))

                is ServerState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun returnToLoginActivity(user: User) {
        intent.apply {
            putExtra(EXTRA_USER, user)
            setResult(RESULT_OK, this)
        }
        finish()
    }
}