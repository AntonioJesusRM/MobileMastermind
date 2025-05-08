package com.example.mobile_mastermind.domain.usecase.remote

import com.example.mobile_mastermind.data.repository.remote.DataProvider
import com.example.mobile_mastermind.data.repository.remote.request.NewGameRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.domain.model.game.NewGameModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostNewGameUseCase @Inject constructor(private val dataProvider: DataProvider) {
    operator fun invoke(postNewGameRequest: NewGameRequest): Flow<BaseResponse<NewGameModel>> {
        return dataProvider.postNewGame(postNewGameRequest)
    }
}