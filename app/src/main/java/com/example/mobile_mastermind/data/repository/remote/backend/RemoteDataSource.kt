package com.example.mobile_mastermind.data.repository.remote.backend

import com.example.mobile_mastermind.data.mapper.game.GetCategoriesMapper
import com.example.mobile_mastermind.data.mapper.users.GetProfileMapper
import com.example.mobile_mastermind.data.repository.preferences.PreferencesDataSource
import com.example.mobile_mastermind.data.repository.remote.request.LoginUserRequest
import com.example.mobile_mastermind.data.repository.remote.request.RegisterRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.data.session.DataUserSession
import com.example.mobile_mastermind.domain.model.game.CategoryModel
import com.example.mobile_mastermind.domain.model.users.GetProfileModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val callApiService: CallApiService,
    private val dataUserSession: DataUserSession,
    private val preferencesDataSource: PreferencesDataSource
) : BaseService() {

    //Login
    fun postLoginUser(loginUserRequest: LoginUserRequest): Flow<BaseResponse<Boolean>> = flow {
        preferencesDataSource.clearPreferences()
        val apiResult = callApiService.callPostLoginUser(loginUserRequest)
        if (apiResult is BaseResponse.Success) {
            apiResult.data.let { response ->
                preferencesDataSource.apply {
                    saveRefreshToken(response.refreshToken ?: "")
                    saveAccessToken(response.accessToken ?: "")
                    saveTokenExpired(response.expireIn ?: 0)
                    saveTokenIssuedAt(System.currentTimeMillis())
                    saveUsername(response.user?.username ?: "")
                    saveProfilePicture(response.user?.image ?: "")
                }
                dataUserSession.username = response.user?.username ?: ""
                dataUserSession.userImage = response.user?.image ?: ""
                dataUserSession.accessToken = response.accessToken ?: ""
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
                    saveTokenIssuedAt(System.currentTimeMillis())
                    saveUsername(response.user?.username ?: "")
                    saveProfilePicture(response.user?.image ?: "")
                }
                dataUserSession.username = response.user?.username ?: ""
                dataUserSession.userImage = response.user?.image ?: ""
                dataUserSession.accessToken = response.accessToken ?: ""
            }
            emit(BaseResponse.Success(data = true))
        } else if (apiResult is BaseResponse.Error) {
            emit(BaseResponse.Error(apiResult.error))
        }
    }

    //Logout
    fun postLogout(): Flow<BaseResponse<Boolean>> = flow {
        val apiResult = callApiService.callPostLogout()
        if (apiResult is BaseResponse.Success) {
            preferencesDataSource.clearPreferences()
            dataUserSession.clearSession()
            emit(BaseResponse.Success(data = true))
        } else if (apiResult is BaseResponse.Error) {
            emit(BaseResponse.Error(apiResult.error))
        }
    }

    //Get all categories
    fun getCategories(): Flow<BaseResponse<List<CategoryModel>>> = flow {
        val apiResult = callApiService.callGetCategories()
        if (apiResult is BaseResponse.Success) {
            emit(BaseResponse.Success(GetCategoriesMapper().fromResponse(apiResult.data)))
        } else if (apiResult is BaseResponse.Error) {
            emit(BaseResponse.Error(apiResult.error))
        }
    }

    //Get profile
    fun getProfile(): Flow<BaseResponse<GetProfileModel>> = flow {
        val apiResult = callApiService.callGetProfile()
        if (apiResult is BaseResponse.Success) {
            emit(BaseResponse.Success(GetProfileMapper().fromResponse(apiResult.data)))
        } else if (apiResult is BaseResponse.Error) {
            emit(BaseResponse.Error(apiResult.error))
        }
    }


}