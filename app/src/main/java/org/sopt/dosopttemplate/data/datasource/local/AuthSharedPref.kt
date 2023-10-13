package org.sopt.dosopttemplate.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.sopt.dosopttemplate.data.model.User

object AuthSharedPref {
    private lateinit var authPref: SharedPreferences
    private val userGson: Gson = GsonBuilder().create()

    fun initAuthPref(context: Context) {
        authPref = context.getSharedPreferences(
            PREF_AUTH_TITLE,
            Context.MODE_PRIVATE,
        )
    }

    fun setAuthUser(user: User) {
        authPref.edit().putString(PREF_AUTH_USER, userGson.toJson(user)).apply()
    }

    fun getAuthUser(): User? {
        val user = authPref.getString(PREF_AUTH_USER, null)
        if (user.isNullOrBlank()) return null
        return userGson.fromJson(user, User::class.java)
    }

    fun clearAuthPref() {
        authPref.edit().clear().apply()
    }

    fun isLogin() : Boolean {
        return authPref.contains(PREF_AUTH_USER)
    }

    private const val PREF_AUTH_TITLE = "AUTH_PREF"
    private const val PREF_AUTH_USER = "AUTH_USER"
}