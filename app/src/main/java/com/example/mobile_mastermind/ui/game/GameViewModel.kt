package com.example.mobile_mastermind.ui.game

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.ui.extension.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {

    // Estado del juego
    private val _uiState = mutableStateOf(GameUiState())
    val uiState: State<GameUiState> = _uiState

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            _uiState.value = GameUiState(
                questions = listOf(
                    Question(
                        id = 1,
                        text = "¿Cuál es el resultado?",
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
                        text = "Kotlin es totalmente compatible con el código Java y puede usarse junto a él en el mismo proyecto.",
                        options = listOf(
                            Option(id = 1, text = "Verdadero", isCorrect = true),
                            Option(id = 2, text = "Falso", isCorrect = false)
                        )
                    ),
                    Question(
                        id = 4,
                        text = "¿Qué hace el modificador 'suspend' en Kotlin?",
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
                            Option(id = 4, text = "Ninguna de las anteriores", isCorrect = false)
                        )
                    ),
                    Question(
                        id = 5,
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
                infoGame = listOf(true, false, true),
                score = 0,
                showResult = false
            )
        }
    }

    fun selectAnswer(optionId: Int) {
        val currentQuestion = _uiState.value.questions[_uiState.value.currentQuestionIndex]
        val isCorrect = currentQuestion.options.any { it.id == optionId && it.isCorrect }

        _uiState.value = _uiState.value.copy(
            selectedAnswer = optionId,
            showResult = true,
            score = if (isCorrect) _uiState.value.score + 10 else _uiState.value.score,
            infoGame = _uiState.value.infoGame + isCorrect
        )
    }

    fun timeOut() {
        _uiState.value = _uiState.value.copy(
            showResult = true
        )
    }

    fun finishGame() {
        Log.d(TAG, "%> Login Clicked")
    }
}
