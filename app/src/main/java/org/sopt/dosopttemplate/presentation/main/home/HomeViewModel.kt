package org.sopt.dosopttemplate.presentation.main.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.sopt.dosopttemplate.data.datasource.local.UserSharedPref
import org.sopt.dosopttemplate.data.model.emptyUser
import org.sopt.dosopttemplate.presentation.main.home.list.UserInfo

class HomeViewModel : ViewModel() {

    private val _myDescription = MutableStateFlow("")
    val myDescription: StateFlow<String> = _myDescription

    fun getMyUserInfo(): UserInfo.MyInfo {
        val prefUserInfo = UserSharedPref.getUserFromPref() ?: emptyUser()
        return UserInfo.MyInfo(prefUserInfo.nickname, prefUserInfo.description)
    }

    fun changeDescriptionToPref(text: String?) {
        if (text == null) return
        val user = UserSharedPref.getUserFromPref().apply {
            if (this != null) description = text
        }
        UserSharedPref.setUserToPref(user ?: return)
        _myDescription.value = text
    }

}