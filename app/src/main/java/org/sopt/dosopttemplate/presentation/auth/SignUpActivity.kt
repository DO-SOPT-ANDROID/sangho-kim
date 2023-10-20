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
import org.sopt.dosopttemplate.util.view.setOnSingleClickListener
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
            viewModel.setEditedUser(
                with(binding) {
                    User(
                        etSignUpId.text.toString().trim(),
                        etSignUpPw.text.toString().trim(),
                        etSignUpNickname.text.toString().trim(),
                        etSignUpDrink.text.toString().trim(),
                    )
                }
            )
            viewModel.checkSignUpAvailable()
        }
    }

    private fun observeSignUpState() {
        viewModel.checkSignUpState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is AuthState.IdError -> snackBar(binding.root) { "아이디의 길이를 확인해주세요." }

                is AuthState.PwError -> snackBar(binding.root) { "비밀번호의 길이를 확인해주세요." }

                is AuthState.EmptyError -> snackBar(binding.root) { "모든 값을 입력해주세요." }

                is AuthState.Success -> {
                    toast("회원가입에 성공했습니다.")
                    returnToLoginActivity()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun returnToLoginActivity() {
        intent.apply {
            putExtra(EXTRA_USER, viewModel.editedUser.value)
            setResult(RESULT_OK, this)
        }
        finish()
    }
}