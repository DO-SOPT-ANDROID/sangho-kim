package org.sopt.dosopttemplate.data.datasource.service

import org.sopt.dosopttemplate.data.model.response.FollowerResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {

    @GET("api/users")
    fun getFollowerList(
        @Query("page") page: Int
    ): Call<FollowerResponseDto>
}