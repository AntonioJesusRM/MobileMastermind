package com.example.mobile_mastermind.ui.review

data class ReviewUiState(
    val category: String = "",
    val pointsEarned: Int = 0,
    val answerCorrect: Int = 0,
    val answerIncorrect: Int = 0,
    val questions: List<QuestionResult> = emptyList()
)

data class QuestionResult(
    val id: String,
    val questionText: String,
    val userAnswer: String,
    val isCorrect: Boolean
)