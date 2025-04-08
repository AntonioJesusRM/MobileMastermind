package com.example.mobile_mastermind.ui.ranking

data class RankingUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val globalRankings: List<RankingItem> = emptyList(),
    val myPosition: Int = 0
)

data class RankingItem(
    val userImg: Int,
    val name: String,
    val points: Int
)
