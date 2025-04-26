package com.example.mobile_mastermind.data.mapper.game

import com.example.mobile_mastermind.data.mapper.ResponseMapper
import com.example.mobile_mastermind.data.repository.remote.response.game.CategoriesResponse
import com.example.mobile_mastermind.data.repository.remote.response.game.GetCategoriesResponse
import com.example.mobile_mastermind.domain.model.game.GetCategoriesModel

class GetCategoriesMapper : ResponseMapper<GetCategoriesResponse, List<GetCategoriesModel>> {
    override fun fromResponse(response: GetCategoriesResponse): List<GetCategoriesModel> {
        return response.categories.orEmpty().mapNotNull { it.toModelOrNull() }
    }

    private fun CategoriesResponse.toModelOrNull(): GetCategoriesModel? {
        return if (id != null && name != null && type != null && categoryImg != null && numberQuestions != null && color != null) {
            GetCategoriesModel(
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