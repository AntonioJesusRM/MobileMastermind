package com.example.mobile_mastermind.data.repository.remote

import com.example.mobile_mastermind.data.repository.preferences.PreferencesDataSource
import com.example.mobile_mastermind.data.repository.remote.backend.RemoteDataSource
import com.example.mobile_mastermind.data.repository.remote.request.LoginUserRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataProvider @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val preferencesDataSource: PreferencesDataSource
) : DataSource {

    //LoginUser
    override fun postLoginUser(loginUserRequest: LoginUserRequest): Flow<BaseResponse<Boolean>> {
        return remoteDataSource.postLoginUser(loginUserRequest)
    }

    //Preferences
    override fun clearPreferences() {
        return preferencesDataSource.clearPreferences()
    }

}