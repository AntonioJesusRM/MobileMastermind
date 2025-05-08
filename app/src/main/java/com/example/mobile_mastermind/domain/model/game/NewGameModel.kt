package com.example.mobile_mastermind.domain.model.game

import com.example.mobile_mastermind.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewGameModel(
    val questions: List<QuestionModel>,
    val categoryId: String,
    val userId: String,
    val points: Int,
    val hits: Int,
    val gameId: String,
) : BaseModel()