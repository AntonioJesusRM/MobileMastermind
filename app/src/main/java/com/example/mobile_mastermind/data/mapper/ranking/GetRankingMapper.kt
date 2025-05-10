package com.example.mobile_mastermind.data.mapper.ranking

import com.example.mobile_mastermind.data.mapper.ResponseMapper
import com.example.mobile_mastermind.data.mapper.users.UserMapper
import com.example.mobile_mastermind.data.repository.remote.response.ranking.GetRankingResponse
import com.example.mobile_mastermind.domain.model.ranking.GetRankingModel


class GetRankingMapper : ResponseMapper<List<GetRankingResponse>?, List<GetRankingModel>> {
    override fun fromResponse(response: List<GetRankingResponse>?): List<GetRankingModel> {
        return response?.mapNotNull { user ->
            val score = user.totalScore
            val user = UserMapper().fromResponse(user.user)

            if (score != null && user.name != "" && user.image != "") {
                GetRankingModel(
                    user = user,
                    totalScore = score
                )
            } else {
                null
            }
        } ?: emptyList()
    }
}