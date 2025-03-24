package com.example.mobile_mastermind.ui.login

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
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState

    fun onUsernameChanged(newUsername: String) {
        _uiState.value = _uiState.value.copy(username = newUsername)
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            println("Logging in with: ${_uiState.value.username} - ${_uiState.value.password}")
        }
    }

    fun onForgotPasswordClicked() {
        Log.d(TAG, "%> Forgot Password Clicked")
    }

    fun onRegisterClicked() {
        Log.d(TAG, "%> Register Clicked")
    }
}