package org.sopt.dosopttemplate.presentation.auth

import android.os.Bundle
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.databinding.ActivitySignUpBinding
import org.sopt.dosopttemplate.presentation.auth.LoginActivity.Companion.EXTRA_USER
import org.sopt.dosopttemplate.util.base.BindingActivity
import org.sopt.dosopttemplate.util.view.setOnSingleClickListener
import snackBar
import toast

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSignUpBtnListener()
    }

    private fun initSignUpBtnListener() {
        binding.btnSignUp.setOnSingleClickListener {
            val editedUser = with(binding) {
                User(
                    etSignUpId.text.toString().trim(),
                    etSignUpPw.text.toString().trim(),
                    etSignUpNickname.text.toString().trim(),
                    etSignUpDrink.text.toString().trim(),
                )
            }
            checkSignUpAvailable(editedUser)
        }
    }

    private fun checkSignUpAvailable(user: User) {
        if (!checkLength(user.id, 6, 10) || !checkLength(user.pw, 8, 12)) {
            snackBar(binding.root) { "아이디와 비밀번호의 길이를 확인해주세요." }
        } else if (listOf(user.nickname, user.drink).any { it.isBlank() }) {
            snackBar(binding.root) { "모든 값을 입력해주세요." }
        } else {
            toast("회원가입에 성공했습니다.")
            returnToLoginActivity(user)
        }
    }

    private fun returnToLoginActivity(editedUser: User) {
        intent.apply {
            putExtra(EXTRA_USER, editedUser)
            setResult(RESULT_OK, this)
        }
        finish()
    }

    private fun checkLength(text: String, min: Int, max: Int): Boolean = text.length in min..max
}