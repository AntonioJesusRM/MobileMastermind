package com.example.mobile_mastermind.data.mapper.game

import com.example.mobile_mastermind.data.mapper.ResponseMapper
import com.example.mobile_mastermind.data.repository.remote.response.game.GetLastUserGameResponse
import com.example.mobile_mastermind.domain.model.game.LastGameModel

class GetLastGameMapper : ResponseMapper<GetLastUserGameResponse?, LastGameModel> {
    override fun fromResponse(response: GetLastUserGameResponse?): LastGameModel {
        return if (response == null) {
            LastGameModel("", "", 0)
        } else {
            LastGameModel(
                img = response.image ?: "",
                color = response.color ?: "",
                points = response.points ?: 0
            )
        }
    }
}