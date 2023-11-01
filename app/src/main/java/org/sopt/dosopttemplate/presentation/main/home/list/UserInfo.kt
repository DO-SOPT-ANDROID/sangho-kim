package org.sopt.dosopttemplate.presentation.main.home.list

import androidx.annotation.DrawableRes

sealed class UserInfo {

    data class MyInfo(
        val nickname: String,
        val description: String? = "",
    ) : UserInfo()

    data class FriendInfo(
        val nickname: String,
        val drink: String? = "",
        val drinkAmount: String? = "",
        @DrawableRes val thumbnail: Int?,
        val description: String? = "",
        val isBirthday: Boolean = false,
        val birthDate: String? = ""
    ) : UserInfo()
}