package org.sopt.dosopttemplate.presentation.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
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

    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setListData()
    }

    private fun initAdapter() {
        _adapter = HomeAdapter(requireContext()) {
            Timber.d("good")
        }
        binding.rvHome.adapter = adapter
    }

    private fun setListData() {
        adapter.addList(mutableListOf(viewModel.getMyUserInfo()))
        adapter.addList(mockList)
    }
}