package org.sopt.dosopttemplate.presentation

import android.os.Bundle
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.databinding.ActivityMainBinding
import org.sopt.dosopttemplate.presentation.LoginActivity.Companion.EXTRA_USER
import org.sopt.dosopttemplate.util.base.BindingActivity
import org.sopt.dosopttemplate.util.intent.getParcelable

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var userData: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserData()
        setUiText()
    }

    private fun getUserData() {
        userData = intent?.getParcelable(EXTRA_USER, User::class.java) ?: return
    }

    private fun setUiText() {
        binding.tvMainNickname.text = userData.nickname
        binding.tvMainId.text = userData.id
        binding.tvMainDrink.text = userData.drink
    }
}