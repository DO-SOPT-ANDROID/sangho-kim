package org.sopt.dosopttemplate.presentation.main.home.list

import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.databinding.ItemMyInfoBinding
import org.sopt.dosopttemplate.util.setOnSingleClickListener

class MyInfoViewHolder(
    val binding: ItemMyInfoBinding,
    private val itemClick: (UserInfo.MyInfo, Int) -> (Unit),
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: UserInfo.MyInfo, position: Int) {
        with(binding) {
            tvHomeMyNickname.text = item.nickname
            tvHomeMyDescription.text = item.description
            btnDescriptionChange.setOnSingleClickListener {
                itemClick(item, position)
            }
        }
    }
}