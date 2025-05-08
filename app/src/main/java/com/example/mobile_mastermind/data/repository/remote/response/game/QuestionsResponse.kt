package com.example.mobile_mastermind.data.repository.remote.response.game

import com.google.gson.annotations.SerializedName

data class QuestionsResponse(
    @SerializedName("_id") val questionId: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("options") val options: List<String>?,
    @SerializedName("correctAnswer") val correctAnswer: String?,
    @SerializedName("image") val questionImage: String?,
)