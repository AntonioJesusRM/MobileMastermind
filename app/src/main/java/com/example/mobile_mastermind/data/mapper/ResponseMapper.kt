package com.example.mobile_mastermind.data.mapper

fun interface ResponseMapper<E, M> {
    fun fromResponse(response: E): M
}