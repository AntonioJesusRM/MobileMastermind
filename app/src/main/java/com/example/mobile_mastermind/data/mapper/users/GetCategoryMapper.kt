package com.example.mobile_mastermind.data.mapper.users

import com.example.mobile_mastermind.data.mapper.ResponseMapper
import com.example.mobile_mastermind.data.repository.remote.response.game.CategoryResponse
import com.example.mobile_mastermind.domain.model.game.CategoryModel

class GetCategoryMapper : ResponseMapper<CategoryResponse, CategoryModel> {
    override fun fromResponse(response: CategoryResponse): CategoryModel {
        return CategoryModel(
            id = response.id ?: "",
            type = response.type ?: "",
            name = response.name ?: "",
            categoryImg = response.categoryImg ?: "",
            numberQuestions = response.numberQuestions ?: 0,
            color = response.color ?: ""
        )
    }
}