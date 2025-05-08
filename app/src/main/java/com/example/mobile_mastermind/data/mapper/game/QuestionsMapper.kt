package com.example.mobile_mastermind.data.mapper.game

import android.util.Log
import com.example.mobile_mastermind.data.mapper.ResponseMapper
import com.example.mobile_mastermind.data.repository.remote.response.game.QuestionsResponse
import com.example.mobile_mastermind.domain.model.game.QuestionModel
import com.example.mobile_mastermind.ui.extension.TAG

class QuestionsMapper : ResponseMapper<List<QuestionsResponse>?, List<QuestionModel>> {
    override fun fromResponse(response: List<QuestionsResponse>?): List<QuestionModel> {
        Log.d(TAG, "%> questions response: $response")
        return response?.mapNotNull { question ->
            val id = question.questionId
            val title = question.title
            val options = question.options
            val correctAnswer = question.correctAnswer
            val image = question.questionImage ?: ""

            if (id != null && title != null && options != null && correctAnswer != null) {
                QuestionModel(
                    questionId = id,
                    title = title,
                    options = options,
                    correctAnswer = correctAnswer,
                    image = image
                )
            } else {
                null
            }
        } ?: emptyList()
    }
}