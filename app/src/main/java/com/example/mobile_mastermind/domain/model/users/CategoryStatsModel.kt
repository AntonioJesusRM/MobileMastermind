package com.example.mobile_mastermind.domain.model.users

import com.example.mobile_mastermind.domain.model.BaseModel
import com.example.mobile_mastermind.domain.model.game.CategoryModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryStatsModel(
    val category: CategoryModel,
    val bestScore: Int,
    val bestQuestion: Int,
    val totalGames: Int,
    val correctAnswers: Int,
    val incorrectAnswers: Int
) : BaseModel()