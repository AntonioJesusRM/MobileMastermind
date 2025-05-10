package com.example.mobile_mastermind.data.mapper.users

import com.example.mobile_mastermind.data.mapper.ResponseMapper
import com.example.mobile_mastermind.data.repository.remote.response.users.GetUserResponse
import com.example.mobile_mastermind.domain.model.users.UserModel


class UserMapper : ResponseMapper<GetUserResponse?, UserModel> {
    override fun fromResponse(response: GetUserResponse?): UserModel {
        return if (response != null) {
            UserModel(
                name = response.username ?: "",
                image = response.image ?: ""
            )
        } else {
            UserModel(name = "", image = "")
        }
    }
}