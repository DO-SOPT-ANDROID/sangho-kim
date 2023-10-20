package org.sopt.dosopttemplate.presentation.auth

sealed class SignUpState {
    object IdError : SignUpState()
    object PwError : SignUpState()
    object EmptyError : SignUpState()
    object Success : SignUpState()
}