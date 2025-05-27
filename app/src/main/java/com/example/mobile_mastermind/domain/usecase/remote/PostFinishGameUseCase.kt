package com.example.mobile_mastermind.domain.usecase.remote

import com.example.mobile_mastermind.data.repository.remote.DataProvider
import com.example.mobile_mastermind.data.repository.remote.request.FinishGameRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.domain.model.game.FinishGameModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostFinishGameUseCase @Inject constructor(private val dataProvider: DataProvider) {
    operator fun invoke(finishGameRequest: FinishGameRequest): Flow<BaseResponse<FinishGameModel>> {
        return dataProvider.postFinishGame(finishGameRequest)
    }
}