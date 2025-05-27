package com.example.mobile_mastermind.domain.model.ranking

import com.example.mobile_mastermind.domain.model.BaseModel
import com.example.mobile_mastermind.domain.model.users.UserModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetRankingModel(
    val user: UserModel,
    val totalScore: Int
) : BaseModel()
