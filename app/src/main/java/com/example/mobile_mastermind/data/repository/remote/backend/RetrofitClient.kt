package com.example.mobile_mastermind.data.repository.remote.backend

import android.util.Log
import com.example.mobile_mastermind.data.constants.GeneralConstants.Companion.BASE_URL
import com.example.mobile_mastermind.data.constants.GeneralConstants.Companion.RETROFIT_TIMEOUT_IN_SECOND
import com.example.mobile_mastermind.data.repository.preferences.PreferencesDataSource
import com.example.mobile_mastermind.ui.extension.TAG
import com.google.gson.GsonBuilder
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.net.ssl.HostnameVerifier

class RetrofitClient @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) {

    companion object {
        const val HEADER_KEY_TOKEN = "Authorization"
        const val HEADER_VALUE_TOKEN_START = "Bearer "
        const val URL = "mobilemastermindapi.onrender.com"
        private const val CERTIFICATE_1 = "sha256/3zFALi4kK2YH+dvPdVhgw31Q+Bx9QwQfVech1JgVRMs="
    }

    val retrofit: Retrofit

    init {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        val certificatePinner =
            CertificatePinner.Builder().add(URL, CERTIFICATE_1).build()
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
        httpClient.interceptors().add(Interceptor { chain ->
            val original = chain.request()

            val request = when {
                needAddBearer(chain.request()) -> {
                    original.newBuilder().header(
                        HEADER_KEY_TOKEN,
                        HEADER_VALUE_TOKEN_START + preferencesDataSource.getAccessToken()
                    ).method(original.method, original.body).build()
                }

                else -> {
                    original.newBuilder().method(original.method, original.body).build()
                }
            }

            chain.proceed(request)
        })

        httpClient.addInterceptor(LoggingInterceptor())
        val gson = GsonBuilder()
            .create()

        retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient.build())
            .callbackExecutor(Executors.newSingleThreadExecutor()).build()
    }

    private fun needAddBearer(request: Request): Boolean {
        val buffer = okio.Buffer()
        request.body?.writeTo(buffer)
        val requestUrl = request.url.toString()

        return when {
            requestUrl.endsWith("api/users/login", true) -> {
                Log.d(TAG, "%> No needAddBearer endsWith(login)")
                false
            }

            requestUrl.endsWith("api/users/register", true) -> {
                Log.d(TAG, "%> No needAddBearer endsWith(register)")
                false
            }

            else -> {
                Log.d(TAG, "%> Get token")
                true
            }
        }
    }

    class LoggingInterceptor : Interceptor {
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