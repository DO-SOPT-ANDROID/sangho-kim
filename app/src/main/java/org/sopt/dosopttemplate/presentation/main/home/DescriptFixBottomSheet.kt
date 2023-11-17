package org.sopt.dosopttemplate.presentation.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.FragmentDescriptFixBinding
import org.sopt.dosopttemplate.util.base.BindingBottomSheetFragment
import org.sopt.dosopttemplate.util.extension.setOnSingleClickListener

class DescriptFixBottomSheet :
    BindingBottomSheetFragment<FragmentDescriptFixBinding>(R.layout.fragment_descript_fix) {

    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSubmitBtnListener()
    }

    private fun initSubmitBtnListener() {
        binding.btnFixSubmit.setOnSingleClickListener {
            viewModel.changeDescriptionToPref(binding.etFix.text.toString())
            dismiss()
        }
    }
}