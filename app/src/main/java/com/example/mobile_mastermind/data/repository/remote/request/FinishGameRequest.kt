package com.example.mobile_mastermind.data.repository.remote.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FinishGameRequest(
    @SerializedName("gameId")
    var gameId: String,
    @SerializedName("results")
    var results: List<ResultsGameRequest>
) : Serializable