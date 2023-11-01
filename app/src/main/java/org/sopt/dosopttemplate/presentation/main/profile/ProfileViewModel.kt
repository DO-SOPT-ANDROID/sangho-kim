package org.sopt.dosopttemplate.presentation.main.profile

import androidx.lifecycle.ViewModel
import org.sopt.dosopttemplate.data.datasource.local.UserSharedPref
import org.sopt.dosopttemplate.data.model.emptyUser

class ProfileViewModel : ViewModel() {

    fun getUserData() = UserSharedPref.getUserFromPref() ?: emptyUser()

    fun clearUserData() {
        UserSharedPref.clearUserPref()
    }
}