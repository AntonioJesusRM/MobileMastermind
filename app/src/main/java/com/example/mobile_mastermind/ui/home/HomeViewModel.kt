package com.example.mobile_mastermind.ui.home

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
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                delay(2000)
                _uiState.value = HomeUiState(
                    userName = "Andrés",
                    points = 300,
                    userImg = R.drawable.ic_launcher_foreground,
                    lastGame = LastGame(1, R.drawable.ic_launcher_foreground, 200),
                    categories = listOf(
                        Category("1", "Kotlin", "Language", 10, R.drawable.ic_launcher_foreground),
                        Category("2", "Swift", "Language", 10, R.drawable.ic_launcher_foreground),
                        Category(
                            "3",
                            "Android Studio",
                            "IDE",
                            10,
                            R.drawable.ic_launcher_foreground
                        ),
                        Category("4", "Xcode", "IDE", 10, R.drawable.ic_launcher_foreground),
                        Category("5", "Xcode", "IDE", 10, R.drawable.ic_launcher_foreground),
                        Category("6", "Xcode", "IDE", 10, R.drawable.ic_launcher_foreground),
                        Category("7", "Xcode", "IDE", 10, R.drawable.ic_launcher_foreground)
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
}