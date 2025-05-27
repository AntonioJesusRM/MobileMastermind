package com.example.mobile_mastermind.ui.ranking

import com.example.mobile_mastermind.domain.model.ranking.GetRankingModel

data class RankingUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val globalRankings: List<GetRankingModel> = emptyList(),
    val myPosition: Int = 0
)
