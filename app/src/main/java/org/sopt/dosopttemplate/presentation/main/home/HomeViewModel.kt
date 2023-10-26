package org.sopt.dosopttemplate.presentation.main.home

import androidx.lifecycle.ViewModel
import org.sopt.dosopttemplate.data.datasource.local.UserSharedPref
import org.sopt.dosopttemplate.data.model.emptyUser
import org.sopt.dosopttemplate.presentation.main.home.list.UserInfo

class HomeViewModel : ViewModel() {

    fun getMyUserInfo() : UserInfo.MyInfo {
        val prefUserInfo = UserSharedPref.getUserFromPref() ?: emptyUser()
        return UserInfo.MyInfo(prefUserInfo.nickname, prefUserInfo.description)
    }

}