package org.sopt.dosopttemplate.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.model.request.SignUpRequestDto
import org.sopt.dosopttemplate.di.ServicePool
import org.sopt.dosopttemplate.domain.entity.User
import org.sopt.dosopttemplate.domain.entity.emptyUser
import org.sopt.dosopttemplate.util.checkLength
import retrofit2.Call
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    private val _checkSignUpState = MutableSharedFlow<AuthState>()
    val checkSignUpState: SharedFlow<AuthState>
        get() = _checkSignUpState

    private val _signUpState: MutableLiveData<ServerState<User>> = MutableLiveData(ServerState.Empty)
    val signUpState: LiveData<ServerState<User>> = _signUpState

    private var user = emptyUser()

    fun setEditedUser(editedUser: User?) {
        user = editedUser ?: return
    }

    fun checkSignUpAvailable() {
        viewModelScope.launch {
            val signUpResult = when {
                !user.id.checkLength(6, 10) -> AuthState.IdError

                !user.pw.checkLength(8, 12) -> AuthState.PwError

                listOf(user.nickname, user.drink).any { it.isBlank() } -> AuthState.EmptyError

                else -> AuthState.Success
            }
            _checkSignUpState.emit(signUpResult)
        }
    }

    fun postSignUpToServer() {
        ServicePool.authService.postSignUp(
            SignUpRequestDto(
                username = user.id,
                password = user.pw,
                nickname = user.nickname
            )
        )
            .enqueue(object : retrofit2.Callback<Unit> {
                override fun onResponse(
                    call: Call<Unit>,
                    response: Response<Unit>,
                ) {
                    if (response.isSuccessful) {
                        _signUpState.value = ServerState.Success(user)
                    } else {
                        _signUpState.value = ServerState.Failure(response.message())
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    _signUpState.value = ServerState.ServerError
                }
            })
    }
}