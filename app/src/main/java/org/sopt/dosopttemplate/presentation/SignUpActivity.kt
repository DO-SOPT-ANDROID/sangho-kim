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

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val resultIntentForLogin = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    private fun setResultIntentForLogin(id: String, pw: String, nickname: String, drink: String) {
        resultIntentForLogin.putExtra(EXTRA_ID, id)
        resultIntentForLogin.putExtra(EXTRA_PW, pw)
        resultIntentForLogin.putExtra(EXTRA_NICKNAME, nickname)
        resultIntentForLogin.putExtra(EXTRA_DRINK, drink)
        setResult(Activity.RESULT_OK, resultIntentForLogin)
        finish()
    }
}