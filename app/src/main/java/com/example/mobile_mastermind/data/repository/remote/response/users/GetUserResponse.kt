package com.example.mobile_mastermind.data.repository.remote.response.users

import com.google.gson.annotations.SerializedName

data class GetUserResponse(
    @SerializedName("username")
    val username: String?,
    @SerializedName("image")
    val image: String?
)
