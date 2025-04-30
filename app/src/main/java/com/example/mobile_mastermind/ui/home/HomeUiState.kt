package com.example.mobile_mastermind.ui.home

import com.example.mobile_mastermind.domain.model.game.CategoryModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userName: String = "",
    val userImg: String = "",
    val points: Int = 0,
    val lastGame: LastGame = LastGame(0, 0, 0),
    val categories: List<CategoryModel> = emptyList(),
)

data class LastGame(
    val id: Int,
    val iconRes: Int,
    val points: Int
)