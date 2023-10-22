package org.sopt.dosopttemplate.presentation.auth

sealed class AuthState {
    object IdError : AuthState()
    object PwError : AuthState()
    object EmptyError : AuthState()
    object Success : AuthState()
}