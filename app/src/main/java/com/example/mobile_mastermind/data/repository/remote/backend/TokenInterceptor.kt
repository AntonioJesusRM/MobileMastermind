package com.example.mobile_mastermind.data.repository.remote.backend

import android.util.Log
import com.example.mobile_mastermind.data.session.TokenManager
import com.example.mobile_mastermind.ui.extension.TAG
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    companion object {
        const val HEADER_KEY_TOKEN = "Authorization"
        const val HEADER_VALUE_TOKEN_START = "Bearer "
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.encodedPath

        if (!needAddBearer(url)) {
            return chain.proceed(originalRequest)
        }

        val token = runBlocking {
            if (tokenManager.needsRefresh()) {
                Log.d(TAG, "%> Refreshing token")
                tokenManager.forceRefreshToken()
            } else {
                Log.d(TAG, "%> take token")
                tokenManager.getAccessToken()
            }
        }

        val authenticatedRequest =
            originalRequest.newBuilder().header(HEADER_KEY_TOKEN, HEADER_VALUE_TOKEN_START + token)
                .build()

        return chain.proceed(authenticatedRequest)
    }

    private fun needAddBearer(url: String): Boolean {

        return when {
            url.endsWith("api/users/login", true) -> {
                Log.d(TAG, "%> No needAddBearer endsWith(login)")
                false
            }

            url.endsWith("api/users/register", true) -> {
                Log.d(TAG, "%> No needAddBearer endsWith(register)")
                false
            }

            else -> {
                Log.d(TAG, "%> Get token")
                true
            }
        }
    }
}