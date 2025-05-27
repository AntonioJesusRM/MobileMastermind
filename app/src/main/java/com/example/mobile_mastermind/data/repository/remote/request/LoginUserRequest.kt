package com.example.mobile_mastermind.data.repository.remote.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginUserRequest(
    @SerializedName("username")
    var username: String,
    @SerializedName("password")
    var password: String,
) : Serializable