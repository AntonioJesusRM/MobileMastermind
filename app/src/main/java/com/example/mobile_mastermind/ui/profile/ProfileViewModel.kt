package com.example.mobile_mastermind.ui.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.data.session.DataUserSession
import com.example.mobile_mastermind.domain.usecase.remote.GetProfileUseCase
import com.example.mobile_mastermind.domain.usecase.remote.PostLogoutUseCase
import com.example.mobile_mastermind.ui.extension.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val dataUserSession: DataUserSession,
    private val postLogoutUseCase: PostLogoutUseCase
) : ViewModel() {
    private val _uiState = mutableStateOf(ProfileUiState())
    val uiState: State<ProfileUiState> = _uiState

    private val _logoutResult = MutableStateFlow<LogoutResult?>(null)
    val logoutResult: StateFlow<LogoutResult?> = _logoutResult

    init {
        getProfile()
    }

    private fun getProfile() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            getProfileUseCase().collect { baseResponse ->
                when (baseResponse) {
                    is BaseResponse.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            profileImg = dataUserSession.userImage,
                            username = dataUserSession.username,
                            points = baseResponse.data.totalPoints,
                            bestScore = baseResponse.data.bestScore,
                            ranking = baseResponse.data.ranking,
                            stats = baseResponse.data.categoryStats
                        )
                    }

                    is BaseResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false
                        )
                        Log.d(TAG, "%> Error: ${baseResponse.error.message}")
                    }
                }
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