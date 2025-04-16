package com.example.mobile_mastermind.data.repository.remote.response

import com.google.gson.annotations.SerializedName

data class SuccessWrapper<T>(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T
)
