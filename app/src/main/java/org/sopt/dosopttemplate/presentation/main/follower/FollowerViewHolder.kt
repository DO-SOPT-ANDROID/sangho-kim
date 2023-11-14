package org.sopt.dosopttemplate.presentation.main.follower

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import org.sopt.dosopttemplate.databinding.ItemFollowerBinding

class FollowerViewHolder(val binding: ItemFollowerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: FollowerResponseDto.User) {
        binding.tvFollowerName.text = item.name
        binding.tvFollowerEmail.text = item.email
        binding.ivFollowerImage.load(item.image) {
            transformations(RoundedCornersTransformation(10.0F))
        }
    }
}