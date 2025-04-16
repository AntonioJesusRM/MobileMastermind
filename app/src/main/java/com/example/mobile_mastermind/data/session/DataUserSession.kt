package com.example.mobile_mastermind.data.session

import java.io.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataUserSession @Inject constructor() : Serializable {
    var username: String = ""
    var userImage: String = ""
    var accessToken: String = ""

    fun haveSession(): Boolean {
        return username.isNotEmpty() && userImage.isNotEmpty() && accessToken.isNotEmpty()
    }

    fun clearSession() {
        username = ""
        userImage = ""
        accessToken = ""
    }

}