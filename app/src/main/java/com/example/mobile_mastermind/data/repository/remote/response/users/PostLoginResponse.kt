package com.example.mobile_mastermind.data.repository.remote.response.users

import com.google.gson.annotations.SerializedName

data class PostLoginResponse(
    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?,
    @SerializedName("expiresIn")
    val expireIn: Int?,
    @SerializedName("user")
    val user: GetUserResponse?
)