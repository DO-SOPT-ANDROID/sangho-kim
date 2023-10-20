package org.sopt.dosopttemplate.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.model.User
import org.sopt.dosopttemplate.data.model.emptyUser

class SignUpViewModel : ViewModel() {

    private val _checkSignUpState = MutableSharedFlow<AuthState>()
    val checkSignUpState: SharedFlow<AuthState>
        get() = _checkSignUpState

    private val _editedUser = MutableStateFlow<User>(emptyUser())
    val editedUser: StateFlow<User> = _editedUser

    fun setEditedUser(user: User) {
        _editedUser.value = user
    }

    fun checkSignUpAvailable() {
        val user = editedUser.value
        viewModelScope.launch {
            val signUpResult = when {
                !checkLength(user.id, 6, 10) -> AuthState.IdError

                !checkLength(user.pw, 8, 12) -> AuthState.PwError

                listOf(user.nickname, user.drink).any { it.isBlank() } -> AuthState.EmptyError

                else -> AuthState.Success
            }
            _checkSignUpState.emit(signUpResult)
        }
    }

    private fun checkLength(text: String, min: Int, max: Int): Boolean = text.length in min..max
}