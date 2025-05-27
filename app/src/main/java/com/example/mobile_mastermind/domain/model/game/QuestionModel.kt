package com.example.mobile_mastermind.domain.model.game

import com.example.mobile_mastermind.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionModel(
    val questionId: String,
    val title: String,
    val correctAnswer: String,
    val image: String,
    val options: List<String>,
) : BaseModel()