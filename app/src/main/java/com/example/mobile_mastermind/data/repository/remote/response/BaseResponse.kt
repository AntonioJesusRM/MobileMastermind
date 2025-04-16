package com.example.mobile_mastermind.data.repository.remote.response

import com.example.mobile_mastermind.domain.model.ErrorModel

sealed class BaseResponse<T> {
    class Success<T>(val data: T) : BaseResponse<T>()
    class Error<T>(val error: ErrorModel) : BaseResponse<T>()
}