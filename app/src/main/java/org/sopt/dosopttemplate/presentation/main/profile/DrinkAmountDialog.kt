package org.sopt.dosopttemplate.presentation.main.profile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.FragmentDrinkAmountDialogBinding
import org.sopt.dosopttemplate.util.base.BindingDialogFragment
import org.sopt.dosopttemplate.util.setOnSingleClickListener

class DrinkAmountDialog :
    BindingDialogFragment<FragmentDrinkAmountDialogBinding>(R.layout.fragment_drink_amount_dialog) {

    private var currentAmount: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBundleArgs()
        initExtBtnListener()
        initConfirmBtnListener()
    }

    private fun getBundleArgs() {
        arguments ?: return
        currentAmount = arguments?.getInt(ARGS_AMOUNT) ?: 0
    }

    private fun initExtBtnListener() {
        binding.btnInviteDialogExit.setOnSingleClickListener { dismiss() }
    }

    private fun initConfirmBtnListener() {
        binding.btnDialogConfirm.setOnSingleClickListener {
            dismiss()
        }
    }

    companion object {
        const val ARGS_AMOUNT = "AMOUNT"

        @JvmStatic
        fun newInstance(currentAmount: Int) = DrinkAmountDialog().apply {
            val args = bundleOf(
                ARGS_AMOUNT to currentAmount
            )
            arguments = args
        }
    }
}