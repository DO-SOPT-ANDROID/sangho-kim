package org.sopt.dosopttemplate.presentation.main

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.datasource.local.UserSharedPref
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.data.model.emptyUser
import org.sopt.dosopttemplate.databinding.ActivityMainBinding
import org.sopt.dosopttemplate.presentation.auth.LoginActivity
import org.sopt.dosopttemplate.util.base.BindingActivity
import org.sopt.dosopttemplate.util.setOnSingleClickListener
import toast

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var userData: User

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserData()
        setUiText()
        initDeleteBtnListener()
        initOnBackPressedListener()
    }

    private fun getUserData() {
        userData = UserSharedPref.getUserFromPref() ?: emptyUser()
    }

    private fun setUiText() {
        if (::userData.isInitialized) {
            with(binding) {
                tvMainNickname.text = userData.nickname
                tvMainId.text = userData.id
                tvMainDrink.text = userData.drink
            }
        } else {
            toast(getString(R.string.login_auto_error))
            returnToLoginActivity()
        }
    }

    private fun initDeleteBtnListener() {
        binding.btnDelete.setOnSingleClickListener {
            UserSharedPref.clearUserPref()
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
                    toast(getString(R.string.back_btn_pressed))
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