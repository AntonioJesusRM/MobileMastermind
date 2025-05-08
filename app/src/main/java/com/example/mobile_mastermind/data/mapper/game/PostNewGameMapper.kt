package com.example.mobile_mastermind.data.mapper.game

import android.util.Log
import com.example.mobile_mastermind.data.mapper.ResponseMapper
import com.example.mobile_mastermind.data.repository.remote.response.game.PostNewGameResponse
import com.example.mobile_mastermind.domain.model.game.NewGameModel
import com.example.mobile_mastermind.ui.extension.TAG

class PostNewGameMapper : ResponseMapper<PostNewGameResponse, NewGameModel> {
    override fun fromResponse(response: PostNewGameResponse): NewGameModel {
        val questions = QuestionsMapper().fromResponse(response.questions)
        Log.d(TAG, "%> mapper questions: $questions")
        return NewGameModel(
            questions = questions,
            categoryId = response.categoryId ?: "",
            userId = response.userId ?: "",
            points = response.points ?: 0,
            hits = response.hits ?: 0,
            gameId = response.gameId ?: ""
        )
    }
}