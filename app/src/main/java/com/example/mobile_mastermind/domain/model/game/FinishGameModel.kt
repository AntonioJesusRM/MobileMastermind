package com.example.mobile_mastermind.domain.model.game

import com.example.mobile_mastermind.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class FinishGameModel(
    val points: Int, val hits: Int
) : BaseModel()