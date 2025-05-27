package com.example.mobile_mastermind.ui.review

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.data.repository.remote.request.FinishGameRequest
import com.example.mobile_mastermind.data.repository.remote.request.ResultsGameRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.domain.usecase.remote.PostFinishGameUseCase
import com.example.mobile_mastermind.ui.extension.TAG
import com.example.mobile_mastermind.ui.game.ResumeGame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val postFinishGameUseCase: PostFinishGameUseCase
) : ViewModel() {
    private val _uiState = mutableStateOf(ReviewUiState())
    val uiState: State<ReviewUiState> = _uiState

    fun loadData(resumeGame: ResumeGame) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            Log.d(TAG, "%> Resume game: $resumeGame")
            val resultGameRequest = resumeGame.questionsResult.map { question ->
                ResultsGameRequest(
                    questionId = question.id,
                    time = question.time,
                    response = question.responseNumber
                )
            }
            Log.d(TAG, "%> Enviado al servidor: $resultGameRequest")
            val finishGameRequest = FinishGameRequest(
                gameId = resumeGame.gameId, results = resultGameRequest
            )
            postFinishGameUseCase(finishGameRequest).collect { baseResponse ->
                when (baseResponse) {
                    is BaseResponse.Success -> {
                        _uiState.value = ReviewUiState(
                            isLoading = false,
                            category = resumeGame.name,
                            pointsEarned = baseResponse.data.points,
                            answerCorrect = resumeGame.answerCorrect,
                            answerIncorrect = resumeGame.answerIncorrect,
                            questions = resumeGame.questionsResult
                        )
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
}