package org.sopt.dosopttemplate.presentation.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.FragmentProfileBinding
import org.sopt.dosopttemplate.presentation.auth.LoginActivity
import org.sopt.dosopttemplate.util.base.BindingFragment
import org.sopt.dosopttemplate.util.setOnSingleClickListener

class ProfileFragment : BindingFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel by activityViewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUserView()
        initDeleteBtnListener()
    }

    private fun initUserView() {
        binding.user = viewModel.getUserData()
    }

    private fun initDeleteBtnListener() {
        binding.btnDelete.setOnSingleClickListener {
            viewModel.clearUserData()
            returnToLoginActivity()
        }
    }

    private fun initAmountBtnListener() {
        binding.btnProfileAmount.setOnSingleClickListener {

        }
    }

    private fun returnToLoginActivity() {
        Intent(requireContext(), LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }
}