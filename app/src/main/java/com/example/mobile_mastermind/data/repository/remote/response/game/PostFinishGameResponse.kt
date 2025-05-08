package com.example.mobile_mastermind.data.repository.remote.response.game

import com.google.gson.annotations.SerializedName

data class PostFinishGameResponse(
    @SerializedName("points") val points: Int?,
    @SerializedName("hits") val hits: Int?
)