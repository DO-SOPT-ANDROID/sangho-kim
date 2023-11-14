package org.sopt.dosopttemplate.presentation.auth

sealed interface ServerState<out T> {
    object Empty : ServerState<Nothing>

    data class Success<T>(
        val data: T,
    ) : ServerState<T>

    data class Failure(
        val msg: String,
    ) : ServerState<Nothing>

    object ServerError : ServerState<Nothing>
}