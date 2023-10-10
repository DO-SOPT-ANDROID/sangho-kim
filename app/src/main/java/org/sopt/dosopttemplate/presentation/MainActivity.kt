package org.sopt.dosopttemplate.presentation

import android.os.Build
import android.os.Bundle
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.databinding.ActivityMainBinding
import org.sopt.dosopttemplate.presentation.LoginActivity.Companion.EXTRA_USER
import org.sopt.dosopttemplate.util.base.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var userData: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserData()
        setUiText()
    }

    private fun getUserData() {
        userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_USER, User::class.java) ?: return
        } else {
            intent.getParcelableExtra(EXTRA_USER) ?: return
        }
    }

    private fun setUiText() {
        binding.tvMainNickname.text = userData.nickname
        binding.tvMainId.text = userData.id
        binding.tvMainDrink.text = userData.drink
    }
}