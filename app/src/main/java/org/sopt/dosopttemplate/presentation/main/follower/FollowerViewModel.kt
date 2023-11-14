package org.sopt.dosopttemplate.presentation.main.follower

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.dosopttemplate.data.datasource.model.response.FollowerResponseDto
import org.sopt.dosopttemplate.di.ServicePool.followerService
import retrofit2.Call
import retrofit2.Response

class FollowerViewModel : ViewModel() {

    private val _followerResult: MutableLiveData<List<FollowerResponseDto.User>?> =
        MutableLiveData()
    val followerResult: LiveData<List<FollowerResponseDto.User>?> = _followerResult

    fun getListFromServer(page: Int) {
        followerService.getFollowerList(page)
            .enqueue(object : retrofit2.Callback<FollowerResponseDto> {
                override fun onResponse(
                    call: Call<FollowerResponseDto>,
                    response: Response<FollowerResponseDto>,
                ) {
                    if (response.isSuccessful) {
                        val responseData: FollowerResponseDto = response.body() ?: return
                        _followerResult.value = responseData.data
                    } else {
                        _followerResult.value = null
                    }
                }

                override fun onFailure(call: Call<FollowerResponseDto>, t: Throwable) {
                    _followerResult.value = null
                }
            })
    }
}