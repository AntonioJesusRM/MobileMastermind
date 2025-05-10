package com.example.mobile_mastermind.data.repository.remote.response.ranking

import com.example.mobile_mastermind.data.repository.remote.response.users.GetUserResponse
import com.google.gson.annotations.SerializedName

data class GetRankingResponse(
    @SerializedName("user") val user: GetUserResponse?,
    @SerializedName("totalScore") val totalScore: Int?
)