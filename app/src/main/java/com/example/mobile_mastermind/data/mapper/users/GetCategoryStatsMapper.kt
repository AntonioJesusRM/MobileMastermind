package com.example.mobile_mastermind.data.mapper.users

import com.example.mobile_mastermind.data.mapper.ResponseMapper
import com.example.mobile_mastermind.data.repository.remote.response.users.CategoryStatsResponse
import com.example.mobile_mastermind.domain.model.users.CategoryStatsModel

class GetCategoryStatsMapper : ResponseMapper<CategoryStatsResponse, CategoryStatsModel> {
    override fun fromResponse(response: CategoryStatsResponse): CategoryStatsModel {
        return CategoryStatsModel(
            categoryName = response.categoryName ?: "",
            categoryColor = response.categoryColor ?: "",
            bestScore = response.bestScore ?: 0,
            bestQuestion = response.betterQuestion ?: 0,
            totalGames = response.totalGames ?: 0,
            correctAnswers = response.totalHits ?: 0,
            incorrectAnswers = response.totalFails ?: 0
        )
    }
}