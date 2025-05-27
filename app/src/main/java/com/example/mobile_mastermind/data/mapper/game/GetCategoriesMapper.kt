package com.example.mobile_mastermind.data.mapper.game

import com.example.mobile_mastermind.data.mapper.ResponseMapper
import com.example.mobile_mastermind.data.repository.remote.response.game.CategoryResponse
import com.example.mobile_mastermind.data.repository.remote.response.game.GetCategoriesResponse
import com.example.mobile_mastermind.domain.model.game.CategoryModel

class GetCategoriesMapper : ResponseMapper<GetCategoriesResponse, List<CategoryModel>> {
    override fun fromResponse(response: GetCategoriesResponse): List<CategoryModel> {
        return response.categories.orEmpty().mapNotNull { it.toModelOrNull() }
    }

    private fun CategoryResponse.toModelOrNull(): CategoryModel? {
        return if (id != null && name != null && type != null && categoryImg != null && numberQuestions != null && color != null) {
            CategoryModel(
                id = id,
                name = name,
                type = type,
                categoryImg = categoryImg,
                numberQuestions = numberQuestions,
                color = color
            )
        } else null
    }
}