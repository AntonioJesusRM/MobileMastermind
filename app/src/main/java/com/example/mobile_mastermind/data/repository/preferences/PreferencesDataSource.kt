package com.example.mobile_mastermind.data.repository.preferences

import com.example.mobile_mastermind.data.session.DataUserSession
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager,
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val dataUserSession: DataUserSession
) {
    fun saveRefreshToken(token: String) {
        encryptedSharedPreferencesManager.saveStringEncryptedSharedPreferences(
            EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_REFRESH_TOKEN, token
        )
    }

    fun getRefreshToken(): String {
        return encryptedSharedPreferencesManager.getStringEncryptedSharedPreferences(
            EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_REFRESH_TOKEN
        )
    }

    fun saveAccessToken(token: String) {
        encryptedSharedPreferencesManager.saveStringEncryptedSharedPreferences(
            EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_ACCESS_TOKEN, token
        )
        dataUserSession.accessToken = token
    }

    fun getAccessToken(): String {
        return encryptedSharedPreferencesManager.getStringEncryptedSharedPreferences(
            EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_ACCESS_TOKEN
        )
    }

    //SHARED PREFERENCES
    fun saveTokenExpired(time: Int) {
        sharedPreferencesManager.saveIntSharedPreferences(
            SharedPreferencesKeys.SHARED_PREFERENCES_TOKEN_EXPIRED, time
        )
    }

    fun getTokenExpired() {
        sharedPreferencesManager.getIntSharedPreferences(
            SharedPreferencesKeys.SHARED_PREFERENCES_TOKEN_EXPIRED
        )
    }

    fun saveUsername(name: String) {
        sharedPreferencesManager.saveStringSharedPreferences(
            SharedPreferencesKeys.SHARED_PREFERENCES_USERNAME, name
        )
        dataUserSession.username = name
    }

    fun getUsername(): String {
        return sharedPreferencesManager.getStringSharedPreferences(
            SharedPreferencesKeys.SHARED_PREFERENCES_USERNAME
        )
    }

    fun saveProfilePicture(image: String) {
        sharedPreferencesManager.saveStringSharedPreferences(
            SharedPreferencesKeys.SHARED_PREFERENCES_IMAGE, image
        )
        dataUserSession.userImage = image
    }

    fun getProfilePicture(): String {
        return sharedPreferencesManager.getStringSharedPreferences(
            SharedPreferencesKeys.SHARED_PREFERENCES_IMAGE
        )
    }

    fun clearPreferences() {
        encryptedSharedPreferencesManager.clearAllPreferences()
        sharedPreferencesManager.clearAllPreferences()
    }
}