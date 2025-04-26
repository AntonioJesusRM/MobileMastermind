package com.example.mobile_mastermind.ui.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.data.session.DataUserSession
import com.example.mobile_mastermind.domain.usecase.remote.PostLogoutUseCase
import com.example.mobile_mastermind.ui.extension.TAG
import com.example.mobile_mastermind.ui.theme.GreenLight
import com.example.mobile_mastermind.ui.theme.RedLight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataUserSession: DataUserSession, private val postLogoutUseCase: PostLogoutUseCase
) : ViewModel() {
    private val _uiState = mutableStateOf(ProfileUiState())
    val uiState: State<ProfileUiState> = _uiState

    private val _logoutResult = MutableStateFlow<LogoutResult?>(null)
    val logoutResult: StateFlow<LogoutResult?> = _logoutResult

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                delay(1000)
                _uiState.value = ProfileUiState(
                    profileImg = dataUserSession.userImage,
                    username = dataUserSession.username,
                    points = 300,
                    bestScore = 300,
                    ranking = 13,
                    stats = listOf(
                        CategoryStats(
                            title = "Kotlin",
                            bestScore = 200,
                            bestQuestion = 82,
                            totalGames = 5,
                            correctAnswers = 45,
                            incorrectAnswers = 5,
                            colorCategory = RedLight
                        ), CategoryStats(
                            title = "Android",
                            bestScore = 400,
                            bestQuestion = 152,
                            totalGames = 10,
                            correctAnswers = 90,
                            incorrectAnswers = 10,
                            colorCategory = GreenLight
                        )
                    )
                )
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(errorMessage = e.localizedMessage ?: "Error desconocido")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun onLogoutClick() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            postLogoutUseCase().collect { baseResponse ->
                when (baseResponse) {
                    is BaseResponse.Success -> {
                        Log.d(TAG, "%> Cierre de sesion correcto.")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false
                        )
                        _logoutResult.value = LogoutResult.Success
                    }

                    is BaseResponse.Error -> {
                        Log.d(TAG, "%> Cierre de sesion incorrecto.")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false
                        )
                        _logoutResult.value =
                            LogoutResult.Error(message = baseResponse.error.message)
                    }
                }
            }
        }
    }

    fun clearState() {
        _logoutResult.value = null
        _uiState.value = ProfileUiState()
    }
}