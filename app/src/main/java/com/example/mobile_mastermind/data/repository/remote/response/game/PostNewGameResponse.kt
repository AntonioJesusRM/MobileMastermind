package com.example.mobile_mastermind.data.repository.remote.response.game

import com.google.gson.annotations.SerializedName

data class PostNewGameResponse(
    @SerializedName("gameId") val gameId: String?,
    @SerializedName("questions") val questions: List<QuestionsResponse>?,
    @SerializedName("categoryId") val categoryId: String?,
    @SerializedName("userId") val userId: String?,
    @SerializedName("points") val points: Int?,
    @SerializedName("hits") val hits: Int?,
)