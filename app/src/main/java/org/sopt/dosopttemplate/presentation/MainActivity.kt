package org.sopt.dosopttemplate.presentation

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.databinding.ActivityMainBinding
import org.sopt.dosopttemplate.presentation.LoginActivity.Companion.EXTRA_USER
import org.sopt.dosopttemplate.util.base.BindingActivity
import org.sopt.dosopttemplate.util.intent.getParcelable
import toast

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var userData: User

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserData()
        setUiText()
        initOnBackPressedListener()
    }

    private fun getUserData() {
        userData = intent?.getParcelable(EXTRA_USER, User::class.java) ?: return
    }

    private fun setUiText() {
        if (::userData.isInitialized) {
            binding.tvMainNickname.text = userData.nickname
            binding.tvMainId.text = userData.id
            binding.tvMainDrink.text = userData.drink
        } else {
            toast("다시 로그인해주세요.")
            returnToLoginActivity()
        }
    }

    private fun returnToLoginActivity() {
        Intent(this, LoginActivity::class.java).apply {
            addFlags(FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
        finish()
    }

    private fun initOnBackPressedListener() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backPressedTime >= BACK_INTERVAL) {
                    backPressedTime = System.currentTimeMillis()
                    toast("버튼을 한번 더 누르면 종료됩니다.")
                } else {
                    finish()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    companion object {
        const val BACK_INTERVAL = 2000
    }
}