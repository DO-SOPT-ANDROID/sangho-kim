package org.sopt.dosopttemplate.presentation.main.follower

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import org.sopt.dosopttemplate.data.datasource.model.response.FollowerResponseDto
import org.sopt.dosopttemplate.databinding.ItemFollowerBinding

class FollowerViewHolder(val binding: ItemFollowerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: FollowerResponseDto.User) {
        binding.tvFollowerName.text = item.firstName + " " + item.lastName
        binding.tvFollowerEmail.text = item.email
        binding.ivFollowerImage.load(item.avatar) {
            transformations(RoundedCornersTransformation(10.0F))
        }
    }
}