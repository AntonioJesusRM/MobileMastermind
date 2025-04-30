package com.example.mobile_mastermind.data.repository.remote

import com.example.mobile_mastermind.data.repository.remote.request.LoginUserRequest
import com.example.mobile_mastermind.data.repository.remote.request.RegisterRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.domain.model.game.CategoryModel
import com.example.mobile_mastermind.domain.model.users.GetProfileModel
import kotlinx.coroutines.flow.Flow

interface DataSource {
    //Login
    fun postLoginUser(loginUserRequest: LoginUserRequest): Flow<BaseResponse<Boolean>>

    //Register
    fun postRegister(registerRequest: RegisterRequest): Flow<BaseResponse<Boolean>>

    //Logout
    fun postLogout(): Flow<BaseResponse<Boolean>>

    //Get all categories
    fun getCategories(): Flow<BaseResponse<List<CategoryModel>>>

    //Get Profile
    fun getProfile(): Flow<BaseResponse<GetProfileModel>>

    //Preferences
    fun clearPreferences()
}