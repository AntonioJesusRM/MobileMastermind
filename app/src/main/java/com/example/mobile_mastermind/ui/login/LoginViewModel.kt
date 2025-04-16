package com.example.mobile_mastermind.ui.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.data.repository.remote.request.LoginUserRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import com.example.mobile_mastermind.domain.usecase.remote.PostLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
) : ViewModel() {
    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState

    private val _loginResult = MutableStateFlow<LoginResult?>(null)
    val loginResult: StateFlow<LoginResult?> = _loginResult

    fun onUsernameChanged(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername)
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onLoginClicked() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            val loginUserRequest =
                LoginUserRequest(_uiState.value.username, _uiState.value.password)
            postLoginUseCase(loginUserRequest).collect { baseResponse ->
                when (baseResponse) {
                    is BaseResponse.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false
                        )
                        _loginResult.value = LoginResult.Success
                    }

                    is BaseResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false
                        )
                        _loginResult.value =
                            LoginResult.Error(message = baseResponse.error.message)
                    }
                }
            }
        }
    }

    fun clearLoginResult() {
        _loginResult.value = null
    }
}