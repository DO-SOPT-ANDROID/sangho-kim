package org.sopt.dosopttemplate.presentation.main.profile

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.os.bundleOf
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.FragmentDrinkAmountDialogBinding
import org.sopt.dosopttemplate.util.base.BindingDialogFragment

class DrinkAmountDialog :
    BindingDialogFragment<FragmentDrinkAmountDialogBinding>(R.layout.fragment_drink_amount_dialog) {

    private var currentAmount: Int = 0

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
            setBackgroundDrawableResource(R.color.transparent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBundleArgs()
    }

    private fun getBundleArgs() {
        arguments ?: return
       currentAmount = arguments?.getInt(ARGS_AMOUNT) ?: 0
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