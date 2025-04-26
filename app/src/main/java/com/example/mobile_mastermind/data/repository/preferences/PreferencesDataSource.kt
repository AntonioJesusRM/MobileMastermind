package com.example.mobile_mastermind.data.repository.preferences

import android.util.Log
import com.example.mobile_mastermind.ui.extension.TAG
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager,
    private val sharedPreferencesManager: SharedPreferencesManager,
) {
    fun saveRefreshToken(token: String) {
        encryptedSharedPreferencesManager.saveStringEncryptedSharedPreferences(
            EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_REFRESH_TOKEN, token
        )
    }

    fun getRefreshToken(): String {
        Log.d(TAG, "%> Estoy en Preferences data source CUIDADO REFRESH")
        return encryptedSharedPreferencesManager.getStringEncryptedSharedPreferences(
            EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_REFRESH_TOKEN
        )
    }

    fun saveAccessToken(token: String) {
        encryptedSharedPreferencesManager.saveStringEncryptedSharedPreferences(
            EncryptedSharedPreferencesKeys.ENCRYPTED_SHARED_PREFERENCES_ACCESS_TOKEN, token
        )
    }

    fun getAccessToken(): String {
        Log.d(TAG, "%> Estoy en Preferences data source CUIDADO ACCESS")
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

    fun getTokenExpired(): Int {
        return sharedPreferencesManager.getIntSharedPreferences(
            SharedPreferencesKeys.SHARED_PREFERENCES_TOKEN_EXPIRED
        )
    }

    fun saveTokenIssuedAt(timestamp: Long) {
        sharedPreferencesManager.saveLongSharedPreferences(
            SharedPreferencesKeys.SHARED_PREFERENCES_TOKEN_ISSUED_AT,
            timestamp
        )
    }

    fun getTokenIssuedAt(): Long {
        return sharedPreferencesManager.getLongSharedPreferences(
            SharedPreferencesKeys.SHARED_PREFERENCES_TOKEN_ISSUED_AT
        )
    }

    fun saveUsername(name: String) {
        sharedPreferencesManager.saveStringSharedPreferences(
            SharedPreferencesKeys.SHARED_PREFERENCES_USERNAME, name
        )
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