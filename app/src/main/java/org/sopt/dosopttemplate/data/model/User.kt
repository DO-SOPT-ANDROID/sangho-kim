package org.sopt.dosopttemplate.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.sopt.dosopttemplate.R

@Parcelize
data class User(
    val id: String,
    val pw: String,
    val nickname: String = "",
    val drink: String = "",
    val drinkAmount: String? = "",
    val thumbnail: Int? = R.drawable.ic_launcher_background,
    val description: String? = "",
    val isBirthday: Boolean = false
) : Parcelable

fun emptyUser() = User(id = "", pw = "")

fun friendUser(
    nickname: String,
    drink: String = "",
    drinkAmount: String? = "",
    description: String? = "",
    thumbnail: Int? = R.drawable.ic_launcher_background,
    isBirthday: Boolean = false
) = User("", "", nickname, drink, drinkAmount, thumbnail, description, isBirthday)

fun isUserEmpty(user: User) =
    listOf(user.id, user.pw, user.nickname, user.drink).any { it.isEmpty() }