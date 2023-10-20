package org.sopt.dosopttemplate.presentation.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SignUpViewModel : ViewModel() {

    private val _checkSignUpState = MutableSharedFlow<SignUpState>()
    val checkSignUpState: SharedFlow<SignUpState>
        get() = _checkSignUpState.asSharedFlow()

}