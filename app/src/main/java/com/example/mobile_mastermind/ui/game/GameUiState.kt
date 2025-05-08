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
    val resumeGame: ResumeGame = ResumeGame("", "", 0, 0, emptyList())
)

data class Question(
    val questionId: String,
    val correctOptionIndex: Int,
    val showResult: Boolean = false,
    val text: String,
    val questionImg: String,
    val options: List<String>,
)

@Parcelize
data class ResumeGame(
    val gameId: String,
    val name: String,
    val answerCorrect: Int,
    val answerIncorrect: Int,
    val questionsResult: List<QuestionResults>
) : Parcelable

@Parcelize
data class QuestionResults(
    val id: String,
    val question: String,
    val response: String,
    val isCorrect: Boolean,
    val responseNumber: String,
    val time: Int
) : Parcelable