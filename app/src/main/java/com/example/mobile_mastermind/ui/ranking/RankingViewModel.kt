package com.example.mobile_mastermind.ui.ranking

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.domain.usecase.remote.GetRankingUseCase
import com.example.mobile_mastermind.ui.extension.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getRankingUseCase: GetRankingUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(RankingUiState())
    val uiState: State<RankingUiState> = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getRankingUseCase().collect { baseResponse ->
                when (baseResponse) {
                    is BaseResponse.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            globalRankings = baseResponse.data
                        )
                    }

                    is BaseResponse.Error -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        Log.d(TAG, "%> Error: ${baseResponse.error.message}")
                    }
                }
            }
        }
    }
}