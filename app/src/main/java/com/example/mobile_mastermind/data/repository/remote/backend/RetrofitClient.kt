package com.example.mobile_mastermind.data.repository.remote.backend

import android.util.Log
import com.example.mobile_mastermind.data.constants.GeneralConstants.Companion.BASE_URL
import com.example.mobile_mastermind.data.constants.GeneralConstants.Companion.RETROFIT_TIMEOUT_IN_SECOND
import com.example.mobile_mastermind.ui.extension.TAG
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.net.ssl.HostnameVerifier

class RetrofitClient @Inject constructor(
    tokenInterceptor: TokenInterceptor, tokenAuthenticator: TokenAuthenticator
) {

    companion object {
        const val URL = "mobilemastermindapi.onrender.com"
        private const val CERTIFICATE_1 = "sha256/3zFALi4kK2YH+dvPdVhgw31Q+Bx9QwQfVech1JgVRMs="
    }

    val retrofit: Retrofit

    init {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        val certificatePinner = CertificatePinner.Builder().add(URL, CERTIFICATE_1).build()
        httpClient.certificatePinner(certificatePinner)

        val hostnamesAllow = listOf(
            URL,
        )
        val hostnameVerifier = HostnameVerifier { hostname, _ ->
            hostname in hostnamesAllow
        }
        httpClient.hostnameVerifier(hostnameVerifier)

        httpClient.connectTimeout(RETROFIT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
            .readTimeout(RETROFIT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
            .writeTimeout(RETROFIT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)

        httpClient.interceptors().clear()
        httpClient.interceptors().add(tokenInterceptor)
        httpClient.authenticator(tokenAuthenticator)
        httpClient.addInterceptor(LogInterceptor())

        retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build()).callbackExecutor(Executors.newSingleThreadExecutor())
                .build()
    }

    class LogInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val response = chain.proceed(originalRequest)

            val responseBodyString = response.body?.string() ?: ""

            Log.d(TAG, "%> Request: ${originalRequest.url}, ${originalRequest.headers}")
            Log.d(TAG, "%> Response: ${response.code}, $responseBodyString")

            val newResponseBody = responseBodyString.toResponseBody(response.body?.contentType())

            return response.newBuilder().body(newResponseBody).build()
        }
    }
}