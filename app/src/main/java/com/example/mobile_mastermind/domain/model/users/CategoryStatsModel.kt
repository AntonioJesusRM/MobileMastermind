package com.example.mobile_mastermind.domain.model.users

import com.example.mobile_mastermind.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryStatsModel(
    val categoryName: String,
    val categoryColor: String,
    val bestScore: Int,
    val bestQuestion: Int,
    val totalGames: Int,
    val correctAnswers: Int,
    val incorrectAnswers: Int
) : BaseModel()