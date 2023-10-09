package org.sopt.dosopttemplate.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.util.base.BindingActivity
import org.sopt.dosopttemplate.util.view.setOnSingleClickListener
import snackBar

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private lateinit var signedUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSignUpBtnListener()
        initLoginBtnListener()
    }

    private val signUpActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val signedId = data.getStringExtra(EXTRA_ID) ?: ""
                val signedPw = data.getStringExtra(EXTRA_PW) ?: ""
                val signedNickname = data.getStringExtra(EXTRA_NICKNAME) ?: ""
                val signedDrink = data.getStringExtra(EXTRA_DRINK) ?: ""
                signedUser = User(signedId, signedPw, signedNickname, signedDrink)
            }
        }
    }

    private fun initSignUpBtnListener() {
        binding.btnSignUp.setOnSingleClickListener {
            Intent(this, SignUpActivity::class.java).apply {
                signUpActivityLauncher.launch(this)
            }
        }
    }

    private fun initLoginBtnListener() {
        binding.btnLogin.setOnSingleClickListener {
            if (::signedUser.isInitialized) {
                if (signedUser.id == binding.etLoginId.text.toString() && signedUser.pw == binding.etLoginPw.text.toString()) {
                    Intent(this, MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(this)
                    }
                    finish()
                } else {
                    snackBar(binding.root.rootView) { "아이디 혹은 비밀번호가 잘못되었습니다." }
                }
            } else {
                snackBar(binding.root.rootView) { "회원가입을 진행해주세요." }
            }
        }
    }

    companion object {
        const val EXTRA_ID = "ID"
        const val EXTRA_PW = "PW"
        const val EXTRA_NICKNAME = "NICKNAME"
        const val EXTRA_DRINK = "DRINK"
    }
}