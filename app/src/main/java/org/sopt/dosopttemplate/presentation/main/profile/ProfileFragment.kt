package org.sopt.dosopttemplate.presentation.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.datasource.local.UserSharedPref
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.data.model.emptyUser
import org.sopt.dosopttemplate.databinding.FragmentProfileBinding
import org.sopt.dosopttemplate.presentation.auth.LoginActivity
import org.sopt.dosopttemplate.util.base.BindingFragment
import org.sopt.dosopttemplate.util.setOnSingleClickListener
import org.sopt.dosopttemplate.util.toast

class ProfileFragment : BindingFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private lateinit var userData: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserData()
        setUiText()
        initDeleteBtnListener()
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
        Intent(requireContext(), LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }
}