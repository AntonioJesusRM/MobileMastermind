package com.example.mobile_mastermind.data.repository.remote.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RefreshTokenRequest(
    @SerializedName("username")
    var username: String,
    @SerializedName("refreshToken")
    var refreshToken: String,
) : Serializable