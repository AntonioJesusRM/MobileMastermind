package com.example.mobile_mastermind.ui.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userName: String = "",
    val userImg: Int = 0,
    val points: Int = 0,
    val lastGame: LastGame = LastGame(0, 0, 0),
    val categories: List<Category> = emptyList(),
)

data class Category(
    val id: Int,
    val name: String,
    val type: String,
    val quizCount: Int,
    val iconRes: Int
)

data class LastGame(
    val id: Int,
    val iconRes: Int,
    val points: Int
)