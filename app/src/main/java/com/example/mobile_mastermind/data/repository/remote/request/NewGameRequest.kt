package com.example.mobile_mastermind.data.repository.remote.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewGameRequest(
    @SerializedName("categoryId")
    var categoryId: String,
) : Serializable