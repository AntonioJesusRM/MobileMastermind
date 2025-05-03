package com.example.mobile_mastermind.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.data.session.DataUserSession
import com.example.mobile_mastermind.domain.model.game.LastGameModel
import com.example.mobile_mastermind.domain.usecase.remote.GetCategoriesUseCase
import com.example.mobile_mastermind.domain.usecase.remote.GetLastGameUseCase
import com.example.mobile_mastermind.domain.usecase.remote.GetTotalPointsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataUserSession: DataUserSession,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getTotalPointsUseCase: GetTotalPointsUseCase,
    private val getLastGameUseCase: GetLastGameUseCase
) : ViewModel() {
    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = _uiState

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            combine(
                getCategoriesUseCase(), getTotalPointsUseCase(), getLastGameUseCase()
            ) { categoriesResponse, pointsResponse, lastGameResponse ->

                val categories = (categoriesResponse as? BaseResponse.Success)?.data ?: emptyList()
                val points = (pointsResponse as? BaseResponse.Success)?.data ?: 0
                val lastGame =
                    (lastGameResponse as? BaseResponse.Success)?.data ?: LastGameModel("", "", 0)

                val errorMessage = when {
                    categoriesResponse is BaseResponse.Error -> categoriesResponse.error.message
                    pointsResponse is BaseResponse.Error -> pointsResponse.error.message
                    lastGameResponse is BaseResponse.Error -> lastGameResponse.error.message
                    else -> null
                }

                Triple(categories, points, lastGame) to errorMessage

            }.collect { (data, error) ->
                val (categories, points, lastGame) = data

                _uiState.value = HomeUiState(
                    isLoading = false,
                    errorMessage = error,
                    userName = dataUserSession.username,
                    userImg = dataUserSession.userImage,
                    points = points,
                    categories = categories,
                    lastGame = lastGame
                )
            }
        }
    }
}