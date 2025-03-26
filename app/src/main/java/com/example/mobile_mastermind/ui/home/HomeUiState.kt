package com.example.mobile_mastermind.ui.home

data class HomeUiState(
    val userName: String = "",
    val userImg: Int = 0,
    val points: Int = 0,
    val lastGame: LastGame = LastGame(0, 0, 0),
    val categories: List<Category> = emptyList(),
)

data class Category(
    val id: String,
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