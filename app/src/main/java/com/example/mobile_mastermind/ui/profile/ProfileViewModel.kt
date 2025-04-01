package com.example.mobile_mastermind.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val _uiState = mutableStateOf(ProfileUiState())
    val uiState: State<ProfileUiState> = _uiState

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState(
                profileImg = R.drawable.ic_launcher_foreground,
                name = "Andrés",
                email = "andres@gmail.com",
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
                        colorCategory = R.color.color_red
                    ),
                    CategoryStats(
                        title = "Android",
                        bestScore = 400,
                        bestQuestion = 152,
                        totalGames = 10,
                        correctAnswers = 90,
                        incorrectAnswers = 10,
                        colorCategory = R.color.color_green
                    )
                )
            )
        }
    }
}