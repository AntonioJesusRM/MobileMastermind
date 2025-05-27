package com.example.mobile_mastermind.data.repository.remote.response.users

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @SerializedName("accessToken")
    val accessToken: String?
)