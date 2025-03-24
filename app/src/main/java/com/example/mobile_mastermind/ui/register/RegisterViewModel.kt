package com.example.mobile_mastermind.ui.register

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_mastermind.ui.extension.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {
    private val _uiState = mutableStateOf(RegisterUiState())
    val uiState: State<RegisterUiState> = _uiState

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

    fun onRegisterClicked() {
        viewModelScope.launch {
            println("Register with: ${_uiState.value.username} - ${_uiState.value.password}")
        }
    }

    fun onLoginClicked() {
        Log.d(TAG, "%> Login Clicked")
    }
}