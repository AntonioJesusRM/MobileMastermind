package com.example.mobile_mastermind.data.repository.remote.request

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.Serializable

data class RegisterRequest(
    @SerializedName("image")
    var image: MultipartBody.Part,
    @SerializedName("username")
    var username: RequestBody,
    @SerializedName("password")
    var password: RequestBody,
    @SerializedName("email")
    var email: RequestBody,
) : Serializable
