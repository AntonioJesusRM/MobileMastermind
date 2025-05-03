package com.example.mobile_mastermind.data.repository.remote.response.game

import com.google.gson.annotations.SerializedName

data class GetLastUserGameResponse(
    @SerializedName("categoryImage") val image: String?,
    @SerializedName("categoryColor") val color: String?,
    @SerializedName("points") val points: Int?
)
