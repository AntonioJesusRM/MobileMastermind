package com.example.mobile_mastermind.ui.ranking

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
class RankingViewModel @Inject constructor() : ViewModel() {

    private val _uiState = mutableStateOf(RankingUiState())
    val uiState: State<RankingUiState> = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                delay(2000)
                _uiState.value = RankingUiState(
                    globalRankings = listOf(
                        RankingItem(R.drawable.ic_launcher_foreground, "Carlos", 2400),
                        RankingItem(R.drawable.ic_launcher_foreground, "Marta", 2300),
                        RankingItem(R.drawable.ic_launcher_foreground, "Javier", 2200),
                        RankingItem(R.drawable.ic_launcher_foreground, "Lucía", 2100),
                        RankingItem(R.drawable.ic_launcher_foreground, "Pedro", 2000),
                        RankingItem(R.drawable.ic_launcher_foreground, "Ana", 1900),
                        RankingItem(R.drawable.ic_launcher_foreground, "Sofía", 1800),
                        RankingItem(R.drawable.ic_launcher_foreground, "Luis", 1700),
                        RankingItem(R.drawable.ic_launcher_foreground, "Elena", 1600),
                        RankingItem(R.drawable.ic_launcher_foreground, "Andrés", 1500),
                        RankingItem(R.drawable.ic_launcher_foreground, "Raúl", 1400),
                        RankingItem(R.drawable.ic_launcher_foreground, "Patricia", 1300),
                        RankingItem(R.drawable.ic_launcher_foreground, "Fernando", 1200),
                        RankingItem(R.drawable.ic_launcher_foreground, "Beatriz", 1100),
                        RankingItem(R.drawable.ic_launcher_foreground, "Daniel", 1000),
                        RankingItem(R.drawable.ic_launcher_foreground, "Cristina", 900),
                        RankingItem(R.drawable.ic_launcher_foreground, "Alberto", 800),
                        RankingItem(R.drawable.ic_launcher_foreground, "Natalia", 700),
                        RankingItem(R.drawable.ic_launcher_foreground, "Iván", 600),
                        RankingItem(R.drawable.ic_launcher_foreground, "Rosa", 500)
                    ), myPosition = 3
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