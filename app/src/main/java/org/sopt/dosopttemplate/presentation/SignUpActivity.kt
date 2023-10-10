package org.sopt.dosopttemplate.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.databinding.ActivitySignUpBinding
import org.sopt.dosopttemplate.presentation.LoginActivity.Companion.EXTRA_USER
import org.sopt.dosopttemplate.util.base.BindingActivity
import org.sopt.dosopttemplate.util.view.setOnSingleClickListener
import snackBar

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSignUpBtnListener()
    }

    private fun initSignUpBtnListener() {
        binding.btnSignUp.setOnSingleClickListener {
            val editedUser = User(
                binding.etSignUpId.text.toString(),
                binding.etSignUpPw.text.toString(),
                binding.etSignUpNickname.text.toString(),
                binding.etSignUpDrink.text.toString(),
            )
            checkSignUpAvailable(editedUser)
        }
    }

    private fun checkSignUpAvailable(editedUser: User) {
        if (!checkLength(editedUser.id, 6, 10) || !checkLength(editedUser.pw, 8, 12)) {
            snackBar(binding.root.rootView) { "아이디와 비밀번호의 길이를 확인해주세요." }
        } else if (listOf(editedUser.nickname, editedUser.drink).any { it.isBlank() }) {
            snackBar(binding.root.rootView) { "모든 값을 입력해주세요." }
        } else {
            returnToLoginActivity(editedUser)
        }
    }

    private fun returnToLoginActivity(editedUser: User) {
        val resultIntentForLogin = Intent().apply {
            putExtra(EXTRA_USER, editedUser)
        }
        setResult(Activity.RESULT_OK, resultIntentForLogin)
        finish()
    }

    private fun checkLength(text: String, min: Int, max: Int): Boolean {
        return text.length in min..max
    }
}