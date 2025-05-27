package com.example.mobile_mastermind.data.repository.remote.response.users

import com.google.gson.annotations.SerializedName

data class CategoryStatsResponse(
    @SerializedName("categoryName") val categoryName: String?,
    @SerializedName("categoryColor") val categoryColor: String?,
    @SerializedName("bestScore") val bestScore: Int?,
    @SerializedName("betterQuestion") val betterQuestion: Int?,
    @SerializedName("totalGames") val totalGames: Int?,
    @SerializedName("totalHits") val totalHits: Int?,
    @SerializedName("totalFails") val totalFails: Int?
)