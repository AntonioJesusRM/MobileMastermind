package com.example.mobile_mastermind.data.repository.remote.response.game

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("_id") val id: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("image") val categoryImg: String?,
    @SerializedName("numberOfQuizzes") val numberQuestions: Int?,
    @SerializedName("color") val color: String?
)
