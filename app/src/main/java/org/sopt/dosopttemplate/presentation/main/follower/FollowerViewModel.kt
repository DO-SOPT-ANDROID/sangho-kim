package org.sopt.dosopttemplate.presentation.main.follower

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.model.response.FollowerResponseDto.User
import org.sopt.dosopttemplate.di.ServicePool.followerService
import org.sopt.dosopttemplate.util.UiState

class FollowerViewModel : ViewModel() {

    private val _followerListState = MutableStateFlow<UiState<List<User>>>(UiState.Empty)
    val followerListState: StateFlow<UiState<List<User>>> = _followerListState

    fun getListFromServer(page: Int) {
        viewModelScope.launch {
            _followerListState.value = UiState.Loading
            runCatching {
                followerService.getFollowerList(page)
            }.onSuccess { response ->
                _followerListState.value = UiState.Success(response.data)
            }.onFailure {
                _followerListState.value = UiState.Failure(it.message.orEmpty())
            }
        }
    }
}