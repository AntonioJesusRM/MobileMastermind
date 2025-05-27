package com.example.mobile_mastermind.ui.profile

import androidx.compose.ui.graphics.Color
import com.example.mobile_mastermind.domain.model.users.CategoryStatsModel

data class ProfileUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val username: String = "",
    val profileImg: String = "",
    val points: Int = 0,
    val bestScore: Int = 0,
    val ranking: Int = 0,
    val stats: List<CategoryStatsModel> = emptyList()
)

data class StatItem(
    val statImg: Int? = null,
    val statBackground: Int? = null,
    val title: String,
    val value: Int? = null,
    val unit: String? = null,
    val statColor: Color
)

sealed class LogoutResult {
    data object Success : LogoutResult()
    data class Error(val message: String) : LogoutResult()
}