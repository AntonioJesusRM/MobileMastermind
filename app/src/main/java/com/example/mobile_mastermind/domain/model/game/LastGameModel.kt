package com.example.mobile_mastermind.domain.model.game

import com.example.mobile_mastermind.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class LastGameModel(
    val img: String,
    val color: String,
    val points: Int
) : BaseModel()