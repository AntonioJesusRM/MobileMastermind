package com.example.mobile_mastermind.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.data.session.DataUserSession
import com.example.mobile_mastermind.domain.usecase.remote.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataUserSession: DataUserSession,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {
    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = _uiState

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            getCategoriesUseCase().collect { baseResponse ->
                when (baseResponse) {
                    is BaseResponse.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false
                        )
                        _uiState.value = HomeUiState(
                            userName = dataUserSession.username,
                            points = 300,
                            userImg = dataUserSession.userImage,
                            lastGame = LastGame(1, R.drawable.ic_launcher_foreground, 200),
                            categories = baseResponse.data
                        )
                    }

                    is BaseResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}