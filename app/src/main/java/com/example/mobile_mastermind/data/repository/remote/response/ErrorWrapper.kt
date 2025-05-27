package com.example.mobile_mastermind.data.repository.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorWrapper(
    @SerializedName("error") val error: ErrorResponse
)