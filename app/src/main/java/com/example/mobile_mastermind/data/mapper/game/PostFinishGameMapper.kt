package com.example.mobile_mastermind.data.mapper.game

import com.example.mobile_mastermind.data.mapper.ResponseMapper
import com.example.mobile_mastermind.data.repository.remote.response.game.PostFinishGameResponse
import com.example.mobile_mastermind.domain.model.game.FinishGameModel

class PostFinishGameMapper : ResponseMapper<PostFinishGameResponse, FinishGameModel> {
    override fun fromResponse(response: PostFinishGameResponse): FinishGameModel {
        return FinishGameModel(
            points = response.points ?: 0,
            hits = response.hits ?: 0
        )
    }
}