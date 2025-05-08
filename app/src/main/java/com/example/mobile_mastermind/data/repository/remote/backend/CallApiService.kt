package com.example.mobile_mastermind.data.repository.remote.backend

import com.example.mobile_mastermind.data.repository.remote.request.FinishGameRequest
import com.example.mobile_mastermind.data.repository.remote.request.LoginUserRequest
import com.example.mobile_mastermind.data.repository.remote.request.NewGameRequest
import com.example.mobile_mastermind.data.repository.remote.request.RegisterRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.data.repository.remote.response.game.GetCategoriesResponse
import com.example.mobile_mastermind.data.repository.remote.response.game.GetLastUserGameResponse
import com.example.mobile_mastermind.data.repository.remote.response.game.GetUserTotalPointsResponse
import com.example.mobile_mastermind.data.repository.remote.response.game.PostFinishGameResponse
import com.example.mobile_mastermind.data.repository.remote.response.game.PostNewGameResponse
import com.example.mobile_mastermind.data.repository.remote.response.users.GetProfileResponse
import com.example.mobile_mastermind.data.repository.remote.response.users.PostLoginResponse
import javax.inject.Inject

class CallApiService @Inject constructor(
    private val apiService: ApiService
) : BaseService() {
    //Login
    suspend fun callPostLoginUser(loginUserRequest: LoginUserRequest): BaseResponse<PostLoginResponse> {
        return apiCall { apiService.postLoginUser(loginUserRequest) }
    }

    //Register
    suspend fun callPostRegister(registerRequest: RegisterRequest): BaseResponse<PostLoginResponse> {
        return apiCall {
            apiService.postRegister(
                username = registerRequest.username,
                password = registerRequest.password,
                image = registerRequest.image,
                email = registerRequest.email
            )
        }
    }

    //Login
    suspend fun callPostLogout(): BaseResponse<Boolean> {
        return apiCall { apiService.postLogout() }
    }

    //Get all categories
    suspend fun callGetCategories(): BaseResponse<GetCategoriesResponse> {
        return apiCall { apiService.getCategories() }
    }

    //Get total points
    suspend fun callGetTotalPoints(): BaseResponse<GetUserTotalPointsResponse> {
        return apiCall { apiService.getTotalPoints() }
    }

    //Get last user game
    suspend fun callGetLastUserGame(): BaseResponse<GetLastUserGameResponse> {
        return apiCall { apiService.getLastUserGame() }
    }

    //Result new game
    suspend fun callPostNewGame(newGameRequest: NewGameRequest): BaseResponse<PostNewGameResponse> {
        return apiCall { apiService.postNewGame(newGameRequest) }
    }

    //Post result game
    suspend fun callPostFinishGame(finishGameRequest: FinishGameRequest): BaseResponse<PostFinishGameResponse> {
        return apiCall { apiService.postResultGame(finishGameRequest) }
    }

    //Get profile
    suspend fun callGetProfile(): BaseResponse<GetProfileResponse> {
        return apiCall { apiService.getProfile() }
    }

}