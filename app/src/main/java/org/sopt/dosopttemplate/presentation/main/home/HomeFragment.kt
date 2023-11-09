package org.sopt.dosopttemplate.presentation.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.datasource.mock.mockList
import org.sopt.dosopttemplate.databinding.FragmentHomeBinding
import org.sopt.dosopttemplate.presentation.main.home.list.HomeAdapter
import org.sopt.dosopttemplate.util.base.BindingFragment
import timber.log.Timber

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private var _adapter: HomeAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private var descriptFixBottomSheet: DescriptFixBottomSheet? = null

    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setListData()
        observeDescription()
    }

    private fun initAdapter() {
        runCatching {
            _adapter = HomeAdapter(requireContext()) {
                descriptFixBottomSheet = DescriptFixBottomSheet()
                descriptFixBottomSheet?.show(
                    parentFragmentManager, FIX_BOTTOM_SHEET
                )
            }
        }.onFailure { error ->
            Timber.e(error)
        }
        binding.rvHome.adapter = adapter
    }

    private fun setListData() {
        adapter.addList(mutableListOf(viewModel.getMyUserInfo()))
        adapter.addList(mockList)
    }

    private fun observeDescription() {
        viewModel.myDescription.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach {
            if (it.isNotBlank()) adapter.changeMyInfo(viewModel.getMyUserInfo())
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
        descriptFixBottomSheet?.dismiss()
    }

    private companion object {
        const val FIX_BOTTOM_SHEET = "fixBottomSheet"
    }
}