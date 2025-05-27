package com.example.mobile_mastermind.domain.usecase.preferences

import com.example.mobile_mastermind.data.repository.remote.DataProvider
import javax.inject.Inject

class ClearPreferencesUseCase @Inject constructor(private val dataProvider: DataProvider) {
    operator fun invoke() = dataProvider.clearPreferences()
}