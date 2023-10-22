package org.sopt.dosopttemplate.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ActivityMainBinding
import org.sopt.dosopttemplate.presentation.main.android.AndroidFragment
import org.sopt.dosopttemplate.presentation.main.home.HomeFragment
import org.sopt.dosopttemplate.presentation.main.profile.ProfileFragment
import org.sopt.dosopttemplate.util.base.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBnvFirstMenu()
        initBnvItemSelectedListener()
    }

    private fun initBnvFirstMenu() {
        supportFragmentManager.findFragmentById(R.id.fcv_main) ?: navigateTo<HomeFragment>()
        binding.bnvMain.selectedItemId = R.id.menu_home
    }

    private fun initBnvItemSelectedListener() {
        binding.bnvMain.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.menu_android -> navigateTo<AndroidFragment>()
                R.id.menu_home -> navigateTo<HomeFragment>()
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
}