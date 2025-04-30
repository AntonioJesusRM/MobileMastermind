package com.example.mobile_mastermind.domain.model.users

import com.example.mobile_mastermind.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetProfileModel(
    val totalPoints: Int,
    val bestScore: Int,
    val ranking: Int,
    val categoryStats: List<CategoryStatsModel>
) : BaseModel()