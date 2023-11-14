package org.sopt.dosopttemplate.presentation.main.follower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopt.dosopttemplate.data.datasource.model.response.FollowerResponseDto
import org.sopt.dosopttemplate.databinding.ItemFollowerBinding
import org.sopt.dosopttemplate.util.ItemDiffCallback

class FollowerAdapter :
    ListAdapter<FollowerResponseDto.User, FollowerViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding: ItemFollowerBinding =
            ItemFollowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    fun addList(newItems: List<FollowerResponseDto.User>) {
        val currentItems = currentList.toMutableList()
        currentItems.addAll(newItems)
        submitList(currentItems)
    }

    companion object {
        private val diffUtil = ItemDiffCallback<FollowerResponseDto.User>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}