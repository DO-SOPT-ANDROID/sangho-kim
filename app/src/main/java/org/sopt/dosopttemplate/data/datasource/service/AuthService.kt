package org.sopt.dosopttemplate.data.datasource.service

import org.sopt.dosopttemplate.data.datasource.model.request.LoginRequestDto
import org.sopt.dosopttemplate.data.datasource.model.request.SignUpRequestDto
import org.sopt.dosopttemplate.data.datasource.model.response.LoginResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/members")
    fun postSignUp(
        @Body request: SignUpRequestDto,
    ): Call<Unit>

    @POST("api/v1/members/sign-in")
    fun postLogin(
        @Body request: LoginRequestDto
    ): Call<LoginResponseDto>
}