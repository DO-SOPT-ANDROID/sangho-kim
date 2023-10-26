package org.sopt.dosopttemplate.presentation.main.home.list

import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.sopt.dosopttemplate.databinding.ItemBirthInfoBinding

class BirthInfoViewHolder(
    val binding: ItemBirthInfoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: UserInfo.FriendInfo) {
        with(binding) {
            ivHomeBirthThumbnail.load(item.thumbnail)
            tvHomeBirthNickname.text = item.nickname
            tvHomeBirthDescription.text = "오늘 " + item.birthDate
        }
    }
}