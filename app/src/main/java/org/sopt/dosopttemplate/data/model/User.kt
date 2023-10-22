package org.sopt.dosopttemplate.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val pw: String,
    val nickname: String = "",
    val drink: String = "",
    val description: String? = "",
    val musicArtist: String? = "",
    val musicTitle: String? = "",
    val isBirthday: Boolean = false
) : Parcelable

fun emptyUser(): User {
    return User(id = "", pw = "")
}

fun isUserEmpty(user: User) =
    listOf(user.id, user.pw, user.nickname, user.drink).any { it.isEmpty() }