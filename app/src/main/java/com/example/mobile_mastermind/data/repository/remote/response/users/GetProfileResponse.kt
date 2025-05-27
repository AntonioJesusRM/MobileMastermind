package com.example.mobile_mastermind.data.repository.remote.response.users

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(
    @SerializedName("totalPoints") val totalPoints: Int?,
    @SerializedName("bestScore") val bestScore: Int?,
    @SerializedName("ranking") val ranking: Int?,
    @SerializedName("categoryStats") val categoryStats: List<CategoryStatsResponse>?
)