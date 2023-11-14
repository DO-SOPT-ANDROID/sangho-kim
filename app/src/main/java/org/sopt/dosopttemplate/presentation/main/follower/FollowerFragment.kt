package org.sopt.dosopttemplate.presentation.main.follower

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.FragmentFollowerBinding
import org.sopt.dosopttemplate.util.base.BindingFragment
import org.sopt.dosopttemplate.util.toast

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
        viewModel.followerResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                adapter.submitList(result)
            } else {
                toast(getString(R.string.server_error))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }
}