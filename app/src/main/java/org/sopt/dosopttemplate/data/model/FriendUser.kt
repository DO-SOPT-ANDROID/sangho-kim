package org.sopt.dosopttemplate.data.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import org.sopt.dosopttemplate.R

@Parcelize
data class FriendUser(
    val nickname: String = "",
    val drink: String = "",
    val drinkAmount: String? = "",
    @DrawableRes val thumbnail: Int? = R.drawable.ic_launcher_background,
    val description: String? = "",
    val isBirthday: Boolean = false,
    val birthDate: String? = ""
) : Parcelable
