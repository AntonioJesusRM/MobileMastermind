package com.example.mobile_mastermind.data.repository.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("type") var type: String?,
    @SerializedName("code") var errorCode: Int?,
    @SerializedName("message") var message: String?
)