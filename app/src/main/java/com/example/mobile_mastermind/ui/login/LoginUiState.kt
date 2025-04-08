package com.example.mobile_mastermind.ui.login

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)

sealed class LoginResult {
    data object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}