package com.example.mobile_mastermind.ui.game

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class GameUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val questions: List<Question> = emptyList(),
    val infoGame: List<Boolean> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswer: Int? = null,
    val resumeGame: ResumeGame = ResumeGame("", 0, 0, 0, emptyList())
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
    val id: Int, val text: String, val isCorrect: Boolean
)

@Parcelize
data class ResumeGame(
    val name: String,
    val score: Int,
    val answerCorrect: Int,
    val answerIncorrect: Int,
    val questionsResult: List<QuestionResults>
) : Parcelable

@Parcelize
data class QuestionResults(
    val id: Int, val question: String, val response: String, val isCorrect: Boolean
) : Parcelable