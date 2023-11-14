package org.sopt.dosopttemplate.presentation.main

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivityMainBinding
import org.sopt.dosopttemplate.presentation.main.android.AndroidFragment
import org.sopt.dosopttemplate.presentation.main.follower.FollowerFragment
import org.sopt.dosopttemplate.presentation.main.home.HomeFragment
import org.sopt.dosopttemplate.presentation.main.profile.ProfileFragment
import org.sopt.dosopttemplate.util.base.BindingActivity
import org.sopt.dosopttemplate.util.toast
import toast

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBnvFirstMenu()
        initOnBackPressedListener()
        initBnvItemSelectedListener()
    }

    private fun initBnvFirstMenu() {
        supportFragmentManager.findFragmentById(R.id.fcv_main) ?: navigateTo<HomeFragment>()
        binding.bnvMain.selectedItemId = R.id.menu_home
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

    private fun initBnvItemSelectedListener() {
        binding.bnvMain.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.menu_android -> navigateTo<AndroidFragment>()
                R.id.menu_home -> navigateTo<HomeFragment>()
                R.id.menu_follower -> navigateTo<FollowerFragment>()
                R.id.menu_profile -> navigateTo<ProfileFragment>()
                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }

    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_main, T::class.java.canonicalName)
        }
    }

    companion object {
        const val BACK_INTERVAL = 2000
    }
}