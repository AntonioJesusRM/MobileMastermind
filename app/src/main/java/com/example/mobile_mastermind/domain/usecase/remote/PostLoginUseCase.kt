package com.example.mobile_mastermind.domain.usecase.remote

import com.example.mobile_mastermind.data.repository.remote.DataProvider
import com.example.mobile_mastermind.data.repository.remote.request.LoginUserRequest
import com.example.mobile_mastermind.data.repository.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(private val dataProvider: DataProvider) {
    operator fun invoke(loginUserRequest: LoginUserRequest): Flow<BaseResponse<Boolean>> {
        return dataProvider.postLoginUser(loginUserRequest)
    }
}