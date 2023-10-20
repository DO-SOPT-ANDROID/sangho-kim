package org.sopt.dosopttemplate.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.datasource.local.UserSharedPref
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.data.model.emptyUser
import org.sopt.dosopttemplate.data.model.isUserEmpty

class LoginViewModel : ViewModel() {

    private val _checkLoginState = MutableSharedFlow<AuthState>()
    val checkLoginState: SharedFlow<AuthState>
        get() = _checkLoginState

    private val signedUser = MutableStateFlow(emptyUser())
    private val editedUser = MutableStateFlow(emptyUser())

    fun setSignedUser(user: User) {
        signedUser.value = user
    }

    fun setEditedUser(user: User) {
        editedUser.value = user
    }

    fun checkLoginAvailable() {
        viewModelScope.launch {
            val loginResult = when {
                isUserEmpty(signedUser.value) -> AuthState.EmptyError

                signedUser.value.id != editedUser.value.id -> AuthState.IdError

                signedUser.value.pw != editedUser.value.pw -> AuthState.PwError

                else -> AuthState.Success
            }
            _checkLoginState.emit(loginResult)
        }
    }

    fun checkAutoLogin() = UserSharedPref.isLogined() && UserSharedPref.getUserFromPref() != null

    fun setAutoLogin() = UserSharedPref.setUserToPref(signedUser.value)

}