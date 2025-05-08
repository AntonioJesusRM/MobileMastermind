package com.example.mobile_mastermind.data.repository.remote

import com.example.mobile_mastermind.data.repository.remote.request.FinishGameRequest
import com.example.mobile_mastermind.data.repository.remote.request.LoginUserRequest
import com.example.mobile_mastermind.data.repository.remote.request.NewGameRequest
import com.example.mobile_mastermind.data.repository.remote.request.RegisterRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.domain.model.game.CategoryModel
import com.example.mobile_mastermind.domain.model.game.FinishGameModel
import com.example.mobile_mastermind.domain.model.game.LastGameModel
import com.example.mobile_mastermind.domain.model.game.NewGameModel
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

    //Get total points
    fun getUserTotalPoints(): Flow<BaseResponse<Int>>


    //Get last user game
    fun getLastGame(): Flow<BaseResponse<LastGameModel>>

    //Post new game
    fun postNewGame(newGameRequest: NewGameRequest): Flow<BaseResponse<NewGameModel>>

    //Post result game
    fun postFinishGame(finishGameRequest: FinishGameRequest): Flow<BaseResponse<FinishGameModel>>

    //Get Profile
    fun getProfile(): Flow<BaseResponse<GetProfileModel>>

    //Preferences
    fun clearPreferences()
}