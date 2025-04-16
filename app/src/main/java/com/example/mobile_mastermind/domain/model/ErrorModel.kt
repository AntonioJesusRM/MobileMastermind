package com.example.mobile_mastermind.domain.model

import kotlinx.parcelize.Parcelize

@Parcelize
class ErrorModel(
    var code: Int = 401,
    var message: String = "unknown"
) : BaseModel()