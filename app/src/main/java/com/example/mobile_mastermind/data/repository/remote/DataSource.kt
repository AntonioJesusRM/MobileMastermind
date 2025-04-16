package com.example.mobile_mastermind.data.repository.remote

import com.example.mobile_mastermind.data.repository.remote.request.LoginUserRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface DataSource {
    //LoginUser
    fun postLoginUser(loginUserRequest: LoginUserRequest): Flow<BaseResponse<Boolean>>

    //Preferences
    fun clearPreferences()
}