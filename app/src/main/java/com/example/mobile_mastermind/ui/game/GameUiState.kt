package com.example.mobile_mastermind.ui.game

data class GameUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val questions: List<Question> = emptyList(),
    val infoGame: List<Boolean> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val score: Int = 0,
    val selectedAnswer: Int? = null
)

data class Question(
    val id: Int,
    val correctOptionId: Int,
    val showResult: Boolean = false,
    val text: String,
    val questionImg: Int? = null,
    val options: List<Option>,
)

data class Option(
    val id: Int,
    val text: String,
    val isCorrect: Boolean
)