package com.example.mobile_mastermind.ui.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userName: String = "",
    val userImg: String = "",
    val points: Int = 0,
    val lastGame: LastGame = LastGame(0, 0, 0),
    val categories: List<Category> = emptyList(),
)

@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val type: String,
    val quizCount: Int,
    val iconRes: Int
) : Parcelable

data class LastGame(
    val id: Int,
    val iconRes: Int,
    val points: Int
)