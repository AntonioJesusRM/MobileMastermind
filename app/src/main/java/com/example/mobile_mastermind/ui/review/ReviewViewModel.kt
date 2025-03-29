package com.example.mobile_mastermind.ui.review

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor() : ViewModel() {
    private val _uiState = mutableStateOf(ReviewUiState())
    val uiState: State<ReviewUiState> = _uiState

    init {
        loadResults()
    }

    private fun loadResults() {
        viewModelScope.launch {
            _uiState.value = ReviewUiState(
                category = "Kotlin",
                pointsEarned = 140,
                answerCorrect = 7,
                answerIncorrect = 3,
                questions = listOf(
                    QuestionResult(
                        id = "1",
                        questionText = "¿Cuál es el resultado de ejecutar el siguiente código?",
                        userAnswer = "1.2",
                        isCorrect = false
                    ),
                    QuestionResult(
                        id = "2",
                        questionText = "¿Cuál de los siguientes opciones es la forma correcta de llamar?",
                        userAnswer = "ver nombre: String = \"Kotlin\"",
                        isCorrect = false
                    ),
                    QuestionResult(
                        id = "3",
                        questionText = "Un string muy muy largo adsifakdsnfnajdnfsjnadskjndfsjandskjndfaskjnfdkjsandfkjsndfaskjna",
                        userAnswer = "Verdadero",
                        isCorrect = true
                    ),
                    QuestionResult(
                        id = "4",
                        questionText = "¿Qué tipo de clase es?",
                        userAnswer = "Data clasa",
                        isCorrect = false
                    )
                )
            )
        }
    }
}