package com.example.mobile_mastermind.ui.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.mobile_mastermind.Home
import com.example.mobile_mastermind.Login
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.Register
import com.example.mobile_mastermind.ui.components.PrimaryButton
import com.example.mobile_mastermind.ui.components.TextClickable
import com.example.mobile_mastermind.ui.components.TextFieldInput
import com.example.mobile_mastermind.ui.extension.TAG

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = hiltViewModel()) {
    val uiState = loginViewModel.uiState.value
    val loginResult by loginViewModel.loginResult.collectAsState()

    LaunchedEffect(loginResult) {
        when (loginResult) {
            is LoginResult.Success -> {
                navController.navigate(Home.route, navOptions {
                    popUpTo(Login.route) { inclusive = true }
                })
                loginViewModel.clearLoginResult()
            }

            is LoginResult.Error -> {
                Log.d(TAG, "%>Error: ${(loginResult as LoginResult.Error).message}")
                loginViewModel.clearLoginResult()
            }

            else -> Unit
        }
    }
    LoginBody(navController, uiState, loginViewModel)
}

@Composable
fun LoginBody(navController: NavController, uiState: LoginUiState, loginViewModel: LoginViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.app_name_first),
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = stringResource(R.string.app_name_second),
                    style = MaterialTheme.typography.displayLarge
                )
            }

            TextFieldInput(
                value = uiState.username,
                onValueChange = { loginViewModel.onUsernameChanged(it) },
                title = stringResource(id = R.string.username),
                placeholder = stringResource(id = R.string.login_username_placeholder),
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            )

            TextFieldInput(
                value = uiState.password,
                onValueChange = { loginViewModel.onPasswordChanged(it) },
                title = stringResource(id = R.string.password),
                placeholder = stringResource(id = R.string.login_password_placeholder),
                isPassword = true,
                enabled = !uiState.isLoading
            )

            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                PrimaryButton(
                    text = stringResource(id = R.string.login_button),
                    onClick = { loginViewModel.onLoginClicked(uiState.username, uiState.password) }
                )
            }
        }

        TextClickable(
            stringResource(R.string.login_register_prompt),
            stringResource(R.string.login_register_prompt_clickable),
            onClick = {
                navController.navigate(Register.route, navOptions {
                    popUpTo(Login.route) { inclusive = true }
                })
                loginViewModel.clearLoginResult()
            },
            enabled = !uiState.isLoading
        )
    }
}
