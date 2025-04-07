package com.example.mobile_mastermind.ui.review

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.ui.game.ResumeGame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor() : ViewModel() {
    private val _uiState = mutableStateOf(ReviewUiState())
    val uiState: State<ReviewUiState> = _uiState

    fun loadData(resumeGame: ResumeGame) {
        viewModelScope.launch {
            try {
                _uiState.value = ReviewUiState(
                    category = resumeGame.name,
                    pointsEarned = resumeGame.score,
                    answerCorrect = resumeGame.answerCorrect,
                    answerIncorrect = resumeGame.answerIncorrect,
                    questions = resumeGame.questionsResult
                )
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(errorMessage = e.localizedMessage ?: "Error desconocido")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}