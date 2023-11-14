package org.sopt.dosopttemplate.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val pw: String,
    val nickname: String = "",
    val drink: String = "",
    val uuid: Int = 0,
    var drinkAmount: Float = 0.0F,
    var description: String? = ""
) : Parcelable

fun emptyUser() = User(id = "", pw = "")

fun isUserEmpty(user: User) =
    listOf(user.id, user.pw, user.nickname, user.drink).any { it.isEmpty() }