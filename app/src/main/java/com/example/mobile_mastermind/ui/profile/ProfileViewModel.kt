package com.example.mobile_mastermind.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.data.session.DataUserSession
import com.example.mobile_mastermind.domain.usecase.preferences.ClearPreferencesUseCase
import com.example.mobile_mastermind.ui.theme.GreenLight
import com.example.mobile_mastermind.ui.theme.RedLight
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataUserSession: DataUserSession,
    private val clearPreferencesUseCase: ClearPreferencesUseCase
) : ViewModel() {
    private val _uiState = mutableStateOf(ProfileUiState())
    val uiState: State<ProfileUiState> = _uiState

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
                    name = dataUserSession.username,
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
        clearPreferencesUseCase()
        dataUserSession.clearSession()
    }
}