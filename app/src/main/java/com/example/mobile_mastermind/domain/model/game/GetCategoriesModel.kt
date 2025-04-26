package com.example.mobile_mastermind.domain.model.game

import com.example.mobile_mastermind.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetCategoriesModel(
    val id: String,
    val type: String,
    val name: String,
    val categoryImg: String,
    val numberQuestions: Int,
    val color: String
) : BaseModel()