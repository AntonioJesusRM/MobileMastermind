package com.example.mobile_mastermind.data.mapper.game

import com.example.mobile_mastermind.data.mapper.ResponseMapper
import com.example.mobile_mastermind.domain.model.game.QuestionModel
import com.example.mobile_mastermind.ui.game.Question

class QuestionsUiMapper : ResponseMapper<List<QuestionModel>, List<Question>> {
    override fun fromResponse(response: List<QuestionModel>): List<Question> {
        return response.map { questionModel ->
            Question(
                questionId = questionModel.questionId,
                correctOptionIndex = questionModel.correctAnswer.toInt(),
                text = questionModel.title,
                showResult = false,
                questionImg = questionModel.image,
                options = questionModel.options
            )
        }
    }
}