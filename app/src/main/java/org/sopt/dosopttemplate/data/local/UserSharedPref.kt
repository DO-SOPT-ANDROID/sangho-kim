package org.sopt.dosopttemplate.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.sopt.dosopttemplate.domain.entity.User

object UserSharedPref {
    private lateinit var userPref: SharedPreferences
    private val userGson: Gson = GsonBuilder().create()

    fun initPref(context: Context) {
        userPref = context.getSharedPreferences(
            PREF_TITLE,
            Context.MODE_PRIVATE,
        )
    }

    fun setUserToPref(user: User) {
        userPref.edit().putString(PREF_USER, userGson.toJson(user)).commit()
    }

    fun getUserFromPref(): User? {
        val user = userPref.getString(PREF_USER, null)
        if (user.isNullOrBlank()) return null
        return userGson.fromJson(user, User::class.java)
    }

    fun clearUserPref() {
        userPref.edit().clear().apply()
    }

    fun isLogined(): Boolean {
        return userPref.contains(PREF_USER)
    }

    private const val PREF_TITLE = "USER_PREF"
    private const val PREF_USER = "USER"
}