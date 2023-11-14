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
import org.sopt.dosopttemplate.util.setOnSingleClickListener
import snackBar
import toast

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSignUpBtnListener()
        observeSignUpAvailable()
        observeSignUpResult()
    }

    private fun initSignUpBtnListener() {
        binding.btnSignUp.setOnSingleClickListener {
            viewModel.setEditedUser(with(binding) {
                User(
                    etSignUpId.text.toString().trim(),
                    etSignUpPw.text.toString().trim(),
                    etSignUpNickname.text.toString().trim(),
                    etSignUpDrink.text.toString().trim(),
                )
            })
            viewModel.checkSignUpAvailable()
        }
    }

    private fun observeSignUpAvailable() {
        viewModel.checkSignUpState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is AuthState.IdError -> snackBar(binding.root) { getString(R.string.sign_in_id_error) }

                is AuthState.PwError -> snackBar(binding.root) { getString(R.string.sign_in_pw_error) }

                is AuthState.EmptyError -> snackBar(binding.root) { getString(R.string.sign_in_empty_error) }

                is AuthState.Success -> {
                    viewModel.postSignUpToServer()
                }
            }
        }.launchIn(lifecycleScope)
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

                else -> return@observe
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