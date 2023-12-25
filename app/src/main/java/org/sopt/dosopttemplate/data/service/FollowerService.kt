package org.sopt.dosopttemplate.data.service

import org.sopt.dosopttemplate.data.model.response.FollowerResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {

    @GET("api/users")
    suspend fun getFollowerList(
        @Query("page") page: Int
    ): FollowerResponseDto
}