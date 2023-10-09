package org.sopt.dosopttemplate.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivitySignUpBinding
import org.sopt.dosopttemplate.presentation.LoginActivity.Companion.EXTRA_DRINK
import org.sopt.dosopttemplate.presentation.LoginActivity.Companion.EXTRA_ID
import org.sopt.dosopttemplate.presentation.LoginActivity.Companion.EXTRA_NICKNAME
import org.sopt.dosopttemplate.presentation.LoginActivity.Companion.EXTRA_PW
import org.sopt.dosopttemplate.util.base.BindingActivity
import org.sopt.dosopttemplate.util.view.setOnSingleClickListener
import snackBar

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val resultIntentForLogin = Intent()

    private lateinit var editedId: String
    private lateinit var editedPw: String
    private lateinit var editedNickname: String
    private lateinit var editedDrink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSignUpBtnListener()
    }

    private fun initSignUpBtnListener() {
        binding.btnSignUp.setOnSingleClickListener {
            getEditedTexts()
            if (checkLength(editedId, 6, 10) && checkLength(editedPw, 8, 12)) {
                setResultIntentForLogin()
            } else if (editedId.isBlank() || editedPw.isBlank() || editedNickname.isBlank() || editedDrink.isBlank()) {
                snackBar(binding.root.rootView) { "모든 값을 입력해주세요." }
            } else {
                snackBar(binding.root.rootView) { "아이디와 비밀번호의 길이를 확인해주세요." }
            }
        }
    }

    private fun getEditedTexts() {
        editedId = binding.etSignUpId.text.toString()
        editedPw = binding.etSignUpPw.text.toString()
        editedNickname = binding.etSignUpNickname.text.toString()
        editedDrink = binding.etSignUpDrink.text.toString()
    }

    private fun checkLength(text: String, min: Int, max: Int): Boolean {
        return text.length in min..max
    }


    private fun setResultIntentForLogin() {
        resultIntentForLogin.putExtra(EXTRA_ID, editedId)
        resultIntentForLogin.putExtra(EXTRA_PW, editedPw)
        resultIntentForLogin.putExtra(EXTRA_NICKNAME, editedNickname)
        resultIntentForLogin.putExtra(EXTRA_DRINK, editedDrink)
        setResult(Activity.RESULT_OK, resultIntentForLogin)
        finish()
    }
}