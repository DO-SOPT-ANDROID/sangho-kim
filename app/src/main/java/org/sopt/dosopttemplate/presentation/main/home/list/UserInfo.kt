package org.sopt.dosopttemplate.presentation.main.home.list

import androidx.annotation.DrawableRes

sealed class UserInfo {

    data class MyInfo(
        val nickname: String,
        val description: String? = "",
    ) : UserInfo()

    data class FriendInfo(
        val nickname: String,
        @DrawableRes val thunbmail: Int?,
        val description: String? = "",
        val drink: String? = "",
        val drinkAmount: String? = ""
    ) : UserInfo()

    data class BirthdayInfo(
        val nickname: String = "",
        @DrawableRes val thunbmail: Int?,
        val birthDate: String? = ""
    ) : UserInfo()
}