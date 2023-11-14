package org.sopt.dosopttemplate.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.local.UserSharedPref
import org.sopt.dosopttemplate.domain.entity.User
import org.sopt.dosopttemplate.domain.entity.emptyUser
import org.sopt.dosopttemplate.domain.entity.isUserEmpty

class LoginViewModel : ViewModel() {

    private val _checkLoginState = MutableSharedFlow<AuthState>()
    val checkLoginState: SharedFlow<AuthState>
        get() = _checkLoginState

    private var signedUser = emptyUser()
    private var editedUser = emptyUser()

    fun setSignedUser(user: User?) {
        signedUser = user ?: return
    }

    fun setEditedUser(user: User?) {
        editedUser = user ?: return
    }

    fun checkLoginAvailable() {
        viewModelScope.launch {
            val loginResult = when {
                isUserEmpty(signedUser) -> AuthState.EmptyError

                signedUser.id != editedUser.id -> AuthState.IdError

                signedUser.pw != editedUser.pw -> AuthState.PwError

                else -> AuthState.Success
            }
            _checkLoginState.emit(loginResult)
        }
    }

    fun checkAutoLogin() = UserSharedPref.isLogined() && UserSharedPref.getUserFromPref() != null

    fun setAutoLogin() = UserSharedPref.setUserToPref(signedUser)

}