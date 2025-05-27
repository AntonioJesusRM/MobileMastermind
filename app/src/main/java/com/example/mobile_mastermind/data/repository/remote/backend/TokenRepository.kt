package com.example.mobile_mastermind.data.repository.remote.backend

import com.example.mobile_mastermind.data.repository.preferences.PreferencesDataSource
import com.example.mobile_mastermind.data.repository.remote.request.RefreshTokenRequest
import com.example.mobile_mastermind.data.session.DataUserSession
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val authApi: AuthApi,
    private val preferencesDataSource: PreferencesDataSource,
    private val dataUserSession: DataUserSession
) {
    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): String {
        val response = authApi.refreshToken(refreshTokenRequest)

        if (response.isSuccessful) {
            val body = response.body()
            body?.data?.let {
                preferencesDataSource.saveAccessToken(token = it.accessToken ?: "")
                preferencesDataSource.saveTokenIssuedAt(System.currentTimeMillis())
                dataUserSession.accessToken = it.accessToken ?: ""
            }
            return body?.data?.accessToken ?: ""
        } else {
            throw Exception("Token refresh failed: ${response.code()}")
        }
    }
}