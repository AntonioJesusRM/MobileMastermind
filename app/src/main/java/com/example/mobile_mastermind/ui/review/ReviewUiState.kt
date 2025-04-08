package com.example.mobile_mastermind.ui.review

import com.example.mobile_mastermind.ui.game.QuestionResults

data class ReviewUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val category: String = "",
    val pointsEarned: Int = 0,
    val answerCorrect: Int = 0,
    val answerIncorrect: Int = 0,
    val questions: List<QuestionResults> = emptyList()
)