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
    private var drinkAmountDialog: DrinkAmountDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUserView()
        initDeleteBtnListener()
        initAmountBtnListener()
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
            drinkAmountDialog = DrinkAmountDialog.newInstance(0)
            drinkAmountDialog?.show(parentFragmentManager, TAG_DIALOG)
        }
    }

    private fun returnToLoginActivity() {
        Intent(requireContext(), LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        drinkAmountDialog?.dismiss()
    }

    private companion object {
        const val TAG_DIALOG = "DIALOG"
    }
}