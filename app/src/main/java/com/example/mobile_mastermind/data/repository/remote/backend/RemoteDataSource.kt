package com.example.mobile_mastermind.data.repository.remote.backend

import com.example.mobile_mastermind.data.repository.preferences.PreferencesDataSource
import com.example.mobile_mastermind.data.repository.remote.request.LoginUserRequest
import com.example.mobile_mastermind.data.repository.remote.request.RegisterRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val callApiService: CallApiService,
    private val preferencesDataSource: PreferencesDataSource
) : BaseService() {

    //LoginUser
    fun postLoginUser(loginUserRequest: LoginUserRequest): Flow<BaseResponse<Boolean>> = flow {
        preferencesDataSource.clearPreferences()
        val apiResult = callApiService.callPostLoginUser(loginUserRequest)
        if (apiResult is BaseResponse.Success) {
            apiResult.data.let { response ->
                preferencesDataSource.apply {
                    saveRefreshToken(response.refreshToken ?: "")
                    saveAccessToken(response.accessToken ?: "")
                    saveTokenExpired(response.expireIn ?: 300)
                    saveUsername(response.user?.username ?: "")
                    saveProfilePicture(response.user?.image ?: "")
                }
            }
            emit(BaseResponse.Success(data = true))
        } else if (apiResult is BaseResponse.Error) {
            emit(BaseResponse.Error(apiResult.error))
        }
    }

    //Register
    fun postRegister(registerRequest: RegisterRequest): Flow<BaseResponse<Boolean>> = flow {
        preferencesDataSource.clearPreferences()
        val apiResult = callApiService.callPostRegister(registerRequest)
        if (apiResult is BaseResponse.Success) {
            apiResult.data.let { response ->
                preferencesDataSource.apply {
                    saveRefreshToken(response.refreshToken ?: "")
                    saveAccessToken(response.accessToken ?: "")
                    saveTokenExpired(response.expireIn ?: 300)
                    saveUsername(response.user?.username ?: "")
                    saveProfilePicture(response.user?.image ?: "")
                }
            }
            emit(BaseResponse.Success(data = true))
        } else if (apiResult is BaseResponse.Error) {
            emit(BaseResponse.Error(apiResult.error))
        }
    }
}