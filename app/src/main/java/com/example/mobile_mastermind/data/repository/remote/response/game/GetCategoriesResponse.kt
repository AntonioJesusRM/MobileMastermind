package com.example.mobile_mastermind.data.repository.remote.response.game

import com.google.gson.annotations.SerializedName

data class GetCategoriesResponse(
    @SerializedName("categories") val categories: List<CategoriesResponse>?,
)