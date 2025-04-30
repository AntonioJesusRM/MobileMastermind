package com.example.mobile_mastermind.data.mapper.users

import com.example.mobile_mastermind.data.mapper.ResponseMapper
import com.example.mobile_mastermind.data.repository.remote.response.users.GetProfileResponse
import com.example.mobile_mastermind.domain.model.users.CategoryStatsModel
import com.example.mobile_mastermind.domain.model.users.GetProfileModel

class GetProfileMapper : ResponseMapper<GetProfileResponse, GetProfileModel> {
    override fun fromResponse(response: GetProfileResponse): GetProfileModel {

        val categoryStats = mutableListOf<CategoryStatsModel>()
        val getCategoryStatsMapper = GetCategoryStatsMapper()

        response.categoryStats?.forEach { categoryStatsResponse ->
            categoryStats.add(getCategoryStatsMapper.fromResponse(categoryStatsResponse))
        }

        return GetProfileModel(
            totalPoints = response.totalPoints ?: 0,
            bestScore = response.bestScore ?: 0,
            ranking = response.ranking ?: 0,
            categoryStats = categoryStats
        )
    }
}