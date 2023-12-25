package org.sopt.dosopttemplate.presentation.main.follower

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.FragmentFollowerBinding
import org.sopt.dosopttemplate.util.UiState
import org.sopt.dosopttemplate.util.base.BindingFragment
import org.sopt.dosopttemplate.util.extension.toast

class FollowerFragment : BindingFragment<FragmentFollowerBinding>(R.layout.fragment_follower) {

    private var _adapter: FollowerAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by activityViewModels<FollowerViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setFollowerList()
        observeFollowerListState()
    }

    private fun initAdapter() {
        _adapter = FollowerAdapter()
        binding.rvFollower.adapter = adapter
    }

    private fun setFollowerList() {
        viewModel.getListFromServer(0)
    }

    private fun observeFollowerListState() {
        viewModel.followerListState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                when (state) {
                    is UiState.Success -> adapter.submitList(state.data)

                    is UiState.Failure -> toast(getString(R.string.server_error))

                    is UiState.Loading -> return@onEach

                    is UiState.Empty -> return@onEach
                }
            }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }
}