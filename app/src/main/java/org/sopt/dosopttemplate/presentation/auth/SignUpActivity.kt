package org.sopt.dosopttemplate.presentation.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.databinding.ActivitySignUpBinding
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
        observeSignUpState()
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

    private fun observeSignUpState() {
        viewModel.checkSignUpState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is AuthState.IdError -> snackBar(binding.root) { getString(R.string.sign_in_id_error) }

                is AuthState.PwError -> snackBar(binding.root) { getString(R.string.sign_in_pw_error) }

                is AuthState.EmptyError -> snackBar(binding.root) { getString(R.string.sign_in_empty_error) }

                is AuthState.Success -> {
                    toast(getString(R.string.sign_in_success))
                    returnToLoginActivity()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun returnToLoginActivity() {
        intent.apply {
            putExtra(EXTRA_USER, viewModel.getEditedUser())
            setResult(RESULT_OK, this)
        }
        finish()
    }
}