package org.sopt.dosopttemplate.presentation.main.home.list

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.databinding.ItemFriendInfoBinding

class FriendInfoViewHolder(
    val binding: ItemFriendInfoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: UserInfo.FriendInfo) {
        with(binding) {
            ivHomeFriendThumbnail.load(item.thumbnail)
            tvHomeFriendNickname.text = item.nickname
            tvHomeFriendDescription.text = item.description

            if (!item.drink.isNullOrBlank() && !item.drinkAmount.isNullOrBlank()) {
                val drinkIcon = when (item.drink) {
                    SOJU -> R.drawable.ic_soju
                    SOMAEK -> R.drawable.ic_somaek
                    BEER -> R.drawable.ic_beer
                    MAK -> R.drawable.ic_mak
                    WINE -> R.drawable.ic_wine
                    else -> return@with
                }
                layoutDrink.isVisible = true
                ivDrinkIcon.load(drinkIcon)
                tvDrinkAmount.text = item.drinkAmount
            } else {
                layoutDrink.isVisible = false
            }
        }
    }

    companion object {
        private const val SOJU = "소주"
        private const val SOMAEK = "소맥"
        private const val BEER = "맥주"
        private const val MAK = "막걸리"
        private const val WINE = "와인"
    }
}