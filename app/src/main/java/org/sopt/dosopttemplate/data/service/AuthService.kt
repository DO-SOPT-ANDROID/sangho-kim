package org.sopt.dosopttemplate.data.service

import org.sopt.dosopttemplate.data.model.request.LoginRequestDto
import org.sopt.dosopttemplate.data.model.request.SignUpRequestDto
import org.sopt.dosopttemplate.data.model.response.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/members")
    suspend fun postSignUp(
        @Body request: SignUpRequestDto,
    ): Unit

    @POST("api/v1/members/sign-in")
    suspend fun postLogin(
        @Body request: LoginRequestDto
    ): LoginResponseDto
}