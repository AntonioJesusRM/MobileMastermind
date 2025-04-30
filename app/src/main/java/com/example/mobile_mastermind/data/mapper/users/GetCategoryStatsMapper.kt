package com.example.mobile_mastermind.data.mapper.users

import com.example.mobile_mastermind.data.mapper.ResponseMapper
import com.example.mobile_mastermind.data.repository.remote.response.users.CategoryStatsResponse
import com.example.mobile_mastermind.domain.model.game.CategoryModel
import com.example.mobile_mastermind.domain.model.users.CategoryStatsModel

class GetCategoryStatsMapper : ResponseMapper<CategoryStatsResponse, CategoryStatsModel> {
    override fun fromResponse(response: CategoryStatsResponse): CategoryStatsModel {
        val getCategoryMapper = GetCategoryMapper()
        val category =
            response.category?.let { getCategoryMapper.fromResponse(it) } ?: CategoryModel(
                "",
                "",
                "",
                "",
                0,
                ""
            )

        return CategoryStatsModel(
            category = category,
            bestScore = response.bestScore ?: 0,
            bestQuestion = response.bestQuestion ?: 0,
            totalGames = response.totalGames ?: 0,
            correctAnswers = response.correctAnswers ?: 0,
            incorrectAnswers = response.incorrectAnswers ?: 0
        )
    }
}