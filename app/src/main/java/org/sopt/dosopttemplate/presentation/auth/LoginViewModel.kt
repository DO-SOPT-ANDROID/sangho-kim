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
import org.sopt.dosopttemplate.data.model.response.LoginResponseDto
import org.sopt.dosopttemplate.di.ServicePool.authService
import org.sopt.dosopttemplate.domain.entity.User
import org.sopt.dosopttemplate.domain.entity.emptyUser
import org.sopt.dosopttemplate.domain.entity.isUserEmpty
import retrofit2.Call
import retrofit2.Response

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

    fun isUserSigned() = signedUser != emptyUser()

    fun setEditedUser(user: User?) {
        editedUser = user ?: return
    }

    fun checkSignedUserAvailable() {
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

    fun checkServerUserAvailable() {
        authService.postLogin(
            LoginRequestDto(
                username = editedUser.id, password = editedUser.pw
            )
        ).enqueue(object : retrofit2.Callback<LoginResponseDto> {
            override fun onResponse(
                call: Call<LoginResponseDto>,
                response: Response<LoginResponseDto>,
            ) {
                if (response.isSuccessful) {
                    _checkServerUserState.value = ServerState.Success(
                        User(
                            uuid = response.body()?.id ?: 0,
                            id = response.body()?.username.orEmpty(),
                            pw = "",
                            nickname = response.body()?.nickname.orEmpty()
                        )
                    )
                } else {
                    _checkServerUserState.value = ServerState.Failure
                }
            }

            override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                _checkServerUserState.value = ServerState.ServerError
            }
        })
    }

    fun checkAutoLogin() = UserSharedPref.isLogined() && UserSharedPref.getUserFromPref() != null

    fun setAutoLogin(user: User) = UserSharedPref.setUserToPref(user)


}