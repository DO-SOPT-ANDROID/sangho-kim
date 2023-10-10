package org.sopt.dosopttemplate.util.intent

import android.content.Intent
import android.os.Build
import org.sopt.dosopttemplate.data.model.User

fun Intent.getParcelableUserExtra(const: String): User? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(const, User::class.java)
    } else {
        getParcelableExtra(const)
    }
}