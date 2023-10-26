package org.sopt.dosopttemplate.presentation.main.home.list

import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.sopt.dosopttemplate.databinding.ItemBirthInfoBinding

class BirthInfoViewHolder(
    val binding: ItemBirthInfoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: UserInfo.BirthdayInfo) {
        with(binding) {
            ivHomeBirthThumbnail.load(item.thunbmail)
            tvHomeBirthNickname.text = item.nickname
            tvHomeBirthDescription.text = "-  " + item.birthDate
        }
    }
}