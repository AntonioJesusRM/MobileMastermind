package com.example.mobile_mastermind.ui.game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {
    private val _uiState = mutableStateOf(GameUiState())
    val uiState: State<GameUiState> = _uiState

    fun loadQuestions(categoryId: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                delay(2000)
                require(categoryId == 1) { "Solo la categoría 1 está disponible en este momento" }
                _uiState.value = GameUiState(
                    questions = listOf(
                        Question(
                            id = 1,
                            text = "¿Cuál es el resultado?",
                            correctOptionId = 1,
                            options = listOf(
                                Option(id = 1, text = "[4]", isCorrect = true),
                                Option(id = 2, text = "[2, 4]", isCorrect = false),
                                Option(id = 3, text = "[8]", isCorrect = false),
                                Option(id = 4, text = "[]", isCorrect = false)
                            ),
                            questionImg = R.drawable.ic_launcher_foreground
                        ),
                        Question(
                            id = 2,
                            correctOptionId = 1,
                            text = "¿Cuál es la forma correcta de declarar una variable inmutable en Kotlin?",
                            options = listOf(
                                Option(id = 1, text = "val nombre = \"Kotlin\"", isCorrect = true),
                                Option(id = 2, text = "let nombre = \"Kotlin\"", isCorrect = false),
                                Option(
                                    id = 3,
                                    text = "var nombre: String = \"Kotlin\"",
                                    isCorrect = false
                                ),
                                Option(
                                    id = 4,
                                    text = "const var nombre = \"Kotlin\"",
                                    isCorrect = false
                                )
                            )
                        ),
                        Question(
                            id = 3,
                            correctOptionId = 1,
                            text = "Kotlin es totalmente compatible con el código Java y puede usarse junto a él en el mismo proyecto.",
                            options = listOf(
                                Option(id = 1, text = "Verdadero", isCorrect = true),
                                Option(id = 2, text = "Falso", isCorrect = false)
                            )
                        ),
                        Question(
                            id = 4,
                            text = "¿Qué hace el modificador 'suspend' en Kotlin?",
                            correctOptionId = 1,
                            options = listOf(
                                Option(
                                    id = 1,
                                    text = "Indica que la función puede ser pausada y reanudada",
                                    isCorrect = true
                                ),
                                Option(
                                    id = 2,
                                    text = "Detiene la ejecución del programa",
                                    isCorrect = false
                                ),
                                Option(
                                    id = 3,
                                    text = "Es equivalente a 'static' en Java",
                                    isCorrect = false
                                ),
                                Option(
                                    id = 4,
                                    text = "Ninguna de las anteriores",
                                    isCorrect = false
                                )
                            )
                        ),
                        Question(
                            id = 5,
                            correctOptionId = 1,
                            text = "¿Qué imprimirá este código?\nval x = listOf(1, 2, 3).map { it * it }.filter { it > 2 }",
                            options = listOf(
                                Option(id = 1, text = "[4, 9]", isCorrect = true),
                                Option(id = 2, text = "[1, 4, 9]", isCorrect = false),
                                Option(id = 3, text = "[9]", isCorrect = false),
                                Option(id = 4, text = "[2, 3]", isCorrect = false)
                            )
                        )
                    ),
                    currentQuestionIndex = 0,
                    score = 0
                )
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(errorMessage = e.localizedMessage ?: "Error desconocido")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun selectAnswer(optionId: Int) {
        val currentQuestion = _uiState.value.questions[_uiState.value.currentQuestionIndex]
        val isCorrect = currentQuestion.options.any { it.id == optionId && it.isCorrect }

        _uiState.value = _uiState.value.copy(
            selectedAnswer = optionId,
            score = if (isCorrect) _uiState.value.score + 10 else _uiState.value.score,
            infoGame = _uiState.value.infoGame + isCorrect
        )
    }

    fun loadNextQuestion() {
        _uiState.value = _uiState.value.copy(
            currentQuestionIndex = _uiState.value.currentQuestionIndex + 1
        )
    }

    fun timeOut(currentQuestion: Question) {
        val updatedQuestions = _uiState.value.questions.map { q ->
            if (q.id == currentQuestion.id) q.copy(showResult = true) else q
        }

        _uiState.value = _uiState.value.copy(
            infoGame = _uiState.value.infoGame + false,
            score = _uiState.value.score,
            questions = updatedQuestions
        )
    }

    fun checkOption(
        isAnswerSelected: Boolean?,
        selectedAnswerId: Int?,
        option: Option,
        question: Question
    ) = when {
        isAnswerSelected == true && (option.id == selectedAnswerId || option.isCorrect) -> option.isCorrect
        isAnswerSelected == false && question.showResult && option.id == question.correctOptionId -> true
        else -> null
    }
}
