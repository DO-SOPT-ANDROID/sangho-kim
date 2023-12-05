package org.sopt.dosopttemplate.presentation.main.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.model.response.FollowerResponseDto
import org.sopt.dosopttemplate.di.ServicePool.followerService

class FollowerViewModel : ViewModel() {

    private val _followerResult: MutableLiveData<List<FollowerResponseDto.User>?> = MutableLiveData()
    val followerResult: LiveData<List<FollowerResponseDto.User>?> = _followerResult

    fun getListFromServer(page: Int) {
        viewModelScope.launch {
            runCatching { followerService.getFollowerList(page) }
                .onSuccess { response ->
                    _followerResult.value = response.data
                }
                .onFailure {
                    _followerResult.value = null
                }
        }
    }
}