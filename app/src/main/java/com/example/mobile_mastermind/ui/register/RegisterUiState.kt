package com.example.mobile_mastermind.ui.register

data class RegisterUiState(
    val username: String = "",
    val userImg: String = "",
    val email: String = "",
    val password: String = "",
    val passwordRepeat: String = "",
    val isLoading: Boolean = false
)

sealed class RegisterResult {
    data object Success : RegisterResult()
    data class Error(val message: String) : RegisterResult()
}