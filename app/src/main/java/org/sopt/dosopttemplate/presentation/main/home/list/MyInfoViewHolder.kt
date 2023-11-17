package org.sopt.dosopttemplate.presentation.main.home.list

import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.databinding.ItemMyInfoBinding
import org.sopt.dosopttemplate.util.extension.setOnSingleClickListener

class MyInfoViewHolder(
    val binding: ItemMyInfoBinding,
    private val itemClick: (UserInfo.MyInfo) -> (Unit),
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: UserInfo.MyInfo) {
        with(binding) {
            tvHomeMyNickname.text = item.nickname
            tvHomeMyDescription.text = item.description
            btnDescriptionChange.setOnSingleClickListener {
                itemClick(item)
            }
        }
    }
}