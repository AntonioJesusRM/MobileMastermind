package com.example.mobile_mastermind.ui.login

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        viewModel = LoginViewModel()
    }

    @Test
    fun testUsername() {
        val username = "antonio"
        viewModel.onUsernameChanged(username)
        assertEquals(username, viewModel.uiState.value.username)
    }
}