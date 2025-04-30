package com.example.mobile_mastermind.data.repository.remote.response.users

import com.example.mobile_mastermind.data.repository.remote.response.game.CategoryResponse
import com.google.gson.annotations.SerializedName

data class CategoryStatsResponse(
    @SerializedName("category") val category: CategoryResponse?,
    @SerializedName("bestScore") val bestScore: Int?,
    @SerializedName("bestQuestion") val bestQuestion: Int?,
    @SerializedName("totalGames") val totalGames: Int?,
    @SerializedName("correctAnswers") val correctAnswers: Int?,
    @SerializedName("incorrectAnswers") val incorrectAnswers: Int?
)