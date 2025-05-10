package com.example.mobile_mastermind.domain.model.users

import com.example.mobile_mastermind.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val image: String
) : BaseModel()
