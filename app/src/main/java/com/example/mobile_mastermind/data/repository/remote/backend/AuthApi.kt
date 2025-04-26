package com.example.mobile_mastermind.data.repository.remote.backend

import com.example.mobile_mastermind.data.repository.remote.request.RefreshTokenRequest
import com.example.mobile_mastermind.data.repository.remote.response.SuccessWrapper
import com.example.mobile_mastermind.data.repository.remote.response.users.RefreshTokenResponse
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/users/refresh")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<SuccessWrapper<RefreshTokenResponse>>
}