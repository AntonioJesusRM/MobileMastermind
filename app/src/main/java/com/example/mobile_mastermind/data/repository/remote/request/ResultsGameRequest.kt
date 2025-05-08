package com.example.mobile_mastermind.data.repository.remote.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResultsGameRequest(
    @SerializedName("questionId")
    var questionId: String,
    @SerializedName("time")
    var time: Int,
    @SerializedName("response")
    var response: String
) : Serializable