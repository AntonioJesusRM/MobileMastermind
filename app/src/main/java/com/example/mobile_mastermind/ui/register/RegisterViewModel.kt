package com.example.mobile_mastermind.ui.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {
    private val _uiState = mutableStateOf(RegisterUiState())
    val uiState: State<RegisterUiState> = _uiState

    private val _registerResult = MutableStateFlow<RegisterResult?>(null)
    val registerResult: StateFlow<RegisterResult?> = _registerResult

    fun onUsernameChanged(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername)
    }

    fun onEmailChanged(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onRepeatPasswordChanged(newRepeatPassword: String) {
        _uiState.value = _uiState.value.copy(passwordRepeat = newRepeatPassword)
    }

    fun onRegisterClicked(
        username: String, email: String, password: String, repeatPassword: String
    ) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                delay(1000)
                require(!(username != "admin" || email != "admin@gmail.com" || password != "admin" || repeatPassword != "admin")) { "El usuario ya existe" }
                _registerResult.value = RegisterResult.Success
            } catch (e: Exception) {
                _registerResult.value = RegisterResult.Error(e.message ?: "Error desconocido")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun clearRegisterResult() {
        _registerResult.value = null
    }
}