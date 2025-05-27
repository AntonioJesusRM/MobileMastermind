package com.example.mobile_mastermind.domain.usecase.remote

import com.example.mobile_mastermind.data.repository.remote.DataProvider
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.domain.model.ranking.GetRankingModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRankingUseCase @Inject constructor(private val dataProvider: DataProvider) {
    operator fun invoke(): Flow<BaseResponse<List<GetRankingModel>>> {
        return dataProvider.getRanking()
    }
}