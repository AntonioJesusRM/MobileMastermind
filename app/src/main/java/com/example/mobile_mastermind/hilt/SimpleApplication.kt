package com.example.mobile_mastermind.hilt

import android.app.Application
import com.example.mobile_mastermind.data.repository.preferences.EncryptedSharedPreferencesManager
import com.example.mobile_mastermind.data.repository.preferences.PreferencesDataSource
import com.example.mobile_mastermind.data.session.DataUserSession
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SimpleApplication : Application() {
    @Inject
    lateinit var preferencesDataSource: PreferencesDataSource

    @Inject
    lateinit var dataUserSession: DataUserSession

    @Inject
    lateinit var encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager

    override fun onCreate() {
        super.onCreate()
        initSession()
    }

    private fun initSession() {
        if (preferencesDataSource.getUsername()
                .isNotBlank() && preferencesDataSource.getProfilePicture()
                .isNotBlank() && preferencesDataSource.getAccessToken().isNotBlank()
        ) {
            dataUserSession.username = preferencesDataSource.getUsername()
            dataUserSession.userImage = preferencesDataSource.getProfilePicture()
            dataUserSession.accessToken = preferencesDataSource.getAccessToken()
        }
    }
}