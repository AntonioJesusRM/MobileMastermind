package com.example.mobile_mastermind.data.session

import android.util.Log
import com.example.mobile_mastermind.data.repository.preferences.PreferencesDataSource
import com.example.mobile_mastermind.data.repository.remote.backend.TokenRepository
import com.example.mobile_mastermind.data.repository.remote.request.RefreshTokenRequest
import com.example.mobile_mastermind.ui.extension.TAG
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
    private val dataUserSession: DataUserSession,
    private val tokenRepository: TokenRepository
) {

    fun needsRefresh(): Boolean {
        val issuedAt = preferencesDataSource.getTokenIssuedAt()
        val expiresIn = preferencesDataSource.getTokenExpired()
        Log.d(TAG, "%> issuedAt: $issuedAt, expiresIn: $expiresIn")
        if (issuedAt == 0L || expiresIn == 0) return true
        val currentTime = System.currentTimeMillis()
        val expireTime = issuedAt + expiresIn * 1000
        Log.d(TAG, "%> NEED REFRESH: $currentTime >= $expireTime")
        return currentTime >= expireTime - 2 * 60 * 1000
    }

    fun getAccessToken(): String {
        return dataUserSession.accessToken
    }

    suspend fun forceRefreshToken(): String {
        Log.d(TAG, "%> forceRefreshToken called")
        val refreshTokenRequest = RefreshTokenRequest(
            username = preferencesDataSource.getUsername(),
            refreshToken = preferencesDataSource.getRefreshToken()
        )
        return tokenRepository.refreshToken(refreshTokenRequest)
    }
}