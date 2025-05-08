package com.example.mobile_mastermind.data.repository.remote

import com.example.mobile_mastermind.data.repository.preferences.PreferencesDataSource
import com.example.mobile_mastermind.data.repository.remote.backend.RemoteDataSource
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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataProvider @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val preferencesDataSource: PreferencesDataSource
) : DataSource {

    //Login
    override fun postLoginUser(loginUserRequest: LoginUserRequest): Flow<BaseResponse<Boolean>> {
        return remoteDataSource.postLoginUser(loginUserRequest)
    }

    //Register
    override fun postRegister(registerRequest: RegisterRequest): Flow<BaseResponse<Boolean>> {
        return remoteDataSource.postRegister(registerRequest)
    }

    //Logout
    override fun postLogout(): Flow<BaseResponse<Boolean>> {
        return remoteDataSource.postLogout()
    }

    //Get all categories
    override fun getCategories(): Flow<BaseResponse<List<CategoryModel>>> {
        return remoteDataSource.getCategories()
    }

    //Get total points
    override fun getUserTotalPoints(): Flow<BaseResponse<Int>> {
        return remoteDataSource.getUserTotalPoints()
    }

    //Get last user game
    override fun getLastGame(): Flow<BaseResponse<LastGameModel>> {
        return remoteDataSource.getLastGame()
    }

    //Post new game
    override fun postNewGame(newGameRequest: NewGameRequest): Flow<BaseResponse<NewGameModel>> {
        return remoteDataSource.postNewGame(newGameRequest)
    }

    //Post result game
    override fun postFinishGame(finishGameRequest: FinishGameRequest): Flow<BaseResponse<FinishGameModel>> {
        return remoteDataSource.postFinishGame(finishGameRequest)
    }

    //Get Profile
    override fun getProfile(): Flow<BaseResponse<GetProfileModel>> {
        return remoteDataSource.getProfile()
    }

    //Preferences
    override fun clearPreferences() {
        return preferencesDataSource.clearPreferences()
    }

}