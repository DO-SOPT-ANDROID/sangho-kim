package org.sopt.dosopttemplate.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.local.UserSharedPref
import org.sopt.dosopttemplate.data.model.request.LoginRequestDto
import org.sopt.dosopttemplate.di.ServicePool.authService
import org.sopt.dosopttemplate.domain.entity.User
import org.sopt.dosopttemplate.domain.entity.emptyUser
import org.sopt.dosopttemplate.domain.entity.isUserEmpty

class LoginViewModel : ViewModel() {

    private val _checkSignedUserState = MutableSharedFlow<AuthState>()
    val checkSignedUserState: SharedFlow<AuthState>
        get() = _checkSignedUserState

    private val _checkServerUserState: MutableLiveData<ServerState<User>> =
        MutableLiveData(ServerState.Empty)
    val checkServerUserState: LiveData<ServerState<User>> = _checkServerUserState

    private var signedUser = emptyUser()
    private var editedUser = emptyUser()

    fun setSignedUser(user: User?) {
        signedUser = user ?: return
    }

    fun getSignedUser() = signedUser

    fun setEditedUser(user: User?) {
        editedUser = user ?: return
    }

    fun checkUserAvailable() {
        if (signedUser != emptyUser()) {
            checkSignedUserAvailable()
        } else {
            checkServerUserAvailable()
        }
    }

    private fun checkSignedUserAvailable() {
        viewModelScope.launch {
            val loginResult = when {
                isUserEmpty(signedUser) -> AuthState.EmptyError

                signedUser.id != editedUser.id -> AuthState.IdError

                signedUser.pw != editedUser.pw -> AuthState.PwError

                else -> AuthState.Success
            }
            _checkSignedUserState.emit(loginResult)
        }
    }

    private fun checkServerUserAvailable() {
        viewModelScope.launch {
            runCatching {
                authService.postLogin(
                    LoginRequestDto(username = editedUser.id, password = editedUser.pw)
                )
            }.onSuccess {
                _checkServerUserState.value = ServerState.Success(
                    User(
                        uuid = it.id, id = it.username, pw = "", nickname = it.nickname
                    )
                )
            }.onFailure {
                _checkServerUserState.value = ServerState.Failure
            }
        }
    }

    fun checkAutoLogin() = UserSharedPref.isLogined() && UserSharedPref.getUserFromPref() != null

    fun setAutoLogin(user: User) = UserSharedPref.setUserToPref(user)


}