package org.sopt.dosopttemplate.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.datasource.local.AuthSharedPref
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.data.model.emptyUser
import org.sopt.dosopttemplate.data.model.isUserEmpty

class LoginViewModel : ViewModel() {

    private val _checkLoginState = MutableSharedFlow<AuthState>()
    val checkLoginState: SharedFlow<AuthState>
        get() = _checkLoginState

    private val _signedUser = MutableStateFlow<User>(emptyUser())
    val signedUser: StateFlow<User> = _signedUser

    private val editedUser = MutableStateFlow<User>(emptyUser())

    fun setSignedUser(user: User) {
        _signedUser.value = user
    }

    fun setEditedUser(user: User) {
        editedUser.value = user
    }

    fun checkAutoLogin() = AuthSharedPref.isLogin() && AuthSharedPref.getAuthUser() != null

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

    fun setAutoLogin() = AuthSharedPref.setAuthUser(signedUser.value)

}