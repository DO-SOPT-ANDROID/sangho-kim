package org.sopt.dosopttemplate.presentation.main.profile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.FragmentDrinkAmountDialogBinding
import org.sopt.dosopttemplate.util.base.BindingDialogFragment
import org.sopt.dosopttemplate.util.extension.setOnSingleClickListener
import kotlin.math.roundToInt

class DrinkAmountDialog :
    BindingDialogFragment<FragmentDrinkAmountDialogBinding>(R.layout.fragment_drink_amount_dialog) {

    private val viewModel by activityViewModels<ProfileViewModel>()

    private var currentAmount: Float = 0.0F

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBundleArgs()
        setSlider()
        initExtBtnListener()
        initConfirmBtnListener()
    }

    private fun getBundleArgs() {
        arguments ?: return
        currentAmount = arguments?.getFloat(ARGS_AMOUNT) ?: 0.0F
    }

    private fun setSlider() {
        binding.sliderDrinkAmount.apply {
            value = currentAmount
            addOnChangeListener { _, value, _ ->
                currentAmount = ((value * 100).roundToInt() / 100.0).toFloat()
            }
        }
    }

    private fun initExtBtnListener() {
        binding.btnInviteDialogExit.setOnSingleClickListener { dismiss() }
    }

    private fun initConfirmBtnListener() {
        binding.btnDialogConfirm.setOnSingleClickListener {
            viewModel.changeDrinkAmountToPref(currentAmount)
            viewModel.updateDrinkAmount(currentAmount)
            dismiss()
        }
    }

    companion object {
        const val ARGS_AMOUNT = "AMOUNT"

        @JvmStatic
        fun newInstance(currentAmount: Float) = DrinkAmountDialog().apply {
            val args = bundleOf(
                ARGS_AMOUNT to currentAmount
            )
            arguments = args
        }
    }
}