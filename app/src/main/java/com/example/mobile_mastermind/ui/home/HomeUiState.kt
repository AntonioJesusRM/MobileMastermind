package com.example.mobile_mastermind.ui.home

import com.example.mobile_mastermind.domain.model.game.CategoryModel
import com.example.mobile_mastermind.domain.model.game.LastGameModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userName: String = "",
    val userImg: String = "",
    val points: Int = 0,
    val lastGame: LastGameModel = LastGameModel("", "", 0),
    val categories: List<CategoryModel> = emptyList(),
)