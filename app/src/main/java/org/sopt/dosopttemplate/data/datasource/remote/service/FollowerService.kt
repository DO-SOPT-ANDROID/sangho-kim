package org.sopt.dosopttemplate.data.datasource.remote.service

import org.sopt.dosopttemplate.data.datasource.model.response.FollowerResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {

    @GET("api/users")
    suspend fun getFollowerList(
        @Query("page") page: Int
    ): FollowerResponseDto
}