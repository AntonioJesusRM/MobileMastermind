package com.example.mobile_mastermind.ui.game

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.data.mapper.game.QuestionsUiMapper
import com.example.mobile_mastermind.data.repository.remote.request.NewGameRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.domain.model.game.CategoryModel
import com.example.mobile_mastermind.domain.usecase.remote.PostNewGameUseCase
import com.example.mobile_mastermind.ui.extension.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val postNewGameUseCase: PostNewGameUseCase,
) : ViewModel() {
    private val _uiState = mutableStateOf(GameUiState())
    val uiState: State<GameUiState> = _uiState

    fun loadQuestions(category: CategoryModel) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val newGameRequest = NewGameRequest(category.id)
            postNewGameUseCase(newGameRequest).collect { baseResponse ->
                when (baseResponse) {
                    is BaseResponse.Success -> {
                        Log.d(TAG, "%> ")
                        if (baseResponse.data.questions.isEmpty()) {
                            _uiState.value = GameUiState(
                                isLoading = false, errorMessage = "No hay preguntas señoria"
                            )
                        } else {
                            _uiState.value = GameUiState(
                                isLoading = false,
                                questions = QuestionsUiMapper().fromResponse(baseResponse.data.questions),
                                currentQuestionIndex = 0,
                                resumeGame = ResumeGame(
                                    gameId = baseResponse.data.gameId,
                                    name = category.name,
                                    answerCorrect = 0,
                                    answerIncorrect = 0,
                                    questionsResult = emptyList()
                                )
                            )
                        }
                    }

                    is BaseResponse.Error -> {
                        Log.d(TAG, "%> Error: ${baseResponse.error.message}")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun addQuestionResult(result: QuestionResults, isCorrect: Boolean) {
        val resume = _uiState.value.resumeGame
        _uiState.value = _uiState.value.copy(
            resumeGame = resume.copy(
                answerCorrect = resume.answerCorrect + if (isCorrect) 1 else 0,
                answerIncorrect = resume.answerIncorrect + if (!isCorrect) 1 else 0,
                questionsResult = resume.questionsResult + result
            ), infoGame = _uiState.value.infoGame + isCorrect
        )
    }

    fun selectAnswer(index: Int, time: Int) {
        val currentQuestion = _uiState.value.questions[_uiState.value.currentQuestionIndex]
        val isCorrect = currentQuestion.correctOptionIndex == index

        val questionResults = QuestionResults(
            id = currentQuestion.questionId,
            question = currentQuestion.text,
            response = currentQuestion.options[index],
            isCorrect = isCorrect,
            responseNumber = index.toString(),
            time = time
        )
        _uiState.value = _uiState.value.copy(selectedAnswer = index)
        addQuestionResult(questionResults, isCorrect)
    }

    fun loadNextQuestion() {
        _uiState.value = _uiState.value.copy(
            currentQuestionIndex = _uiState.value.currentQuestionIndex + 1,
        )
    }

    fun timeOut(currentQuestion: Question) {
        val indexQuestion = _uiState.value.currentQuestionIndex
        val updatedQuestions = _uiState.value.questions.toMutableList()

        updatedQuestions[indexQuestion] = updatedQuestions[indexQuestion].copy(showResult = true)

        val questionResults = QuestionResults(
            id = currentQuestion.questionId,
            question = currentQuestion.text,
            response = "",
            isCorrect = false,
            responseNumber = "",
            time = 0
        )

        _uiState.value = _uiState.value.copy(questions = updatedQuestions)
        addQuestionResult(questionResults, false)
    }

    fun checkOption(
        isAnswerSelected: Boolean?, selectedAnswerIndex: Int?, optionIndex: Int, question: Question
    ) = when {
        isAnswerSelected == true && (optionIndex == selectedAnswerIndex) -> optionIndex == question.correctOptionIndex
        isAnswerSelected == false && question.showResult && optionIndex == question.correctOptionIndex -> true
        else -> null
    }
}
