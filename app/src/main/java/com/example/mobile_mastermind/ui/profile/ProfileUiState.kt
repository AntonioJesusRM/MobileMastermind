package com.example.mobile_mastermind.ui.profile

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val profileImg: Int? = null,
    val points: Int = 0,
    val bestScore: Int = 0,
    val ranking: Int = 0,
    val stats: List<CategoryStats> = emptyList()
)

data class CategoryStats(
    val title: String,
    val bestScore: Int,
    val bestQuestion: Int,
    val totalGames: Int,
    val correctAnswers: Int,
    val incorrectAnswers: Int,
    val colorCategory: Int
)

data class StatItem(
    val statImg: Int? = null,
    val statBackground: Int? = null,
    val title: String,
    val value: Int? = null,
    val unit: String? = null,
    val statColor: Int
)