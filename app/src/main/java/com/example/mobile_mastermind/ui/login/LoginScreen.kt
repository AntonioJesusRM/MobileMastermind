package com.example.mobile_mastermind.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.ui.components.PrimaryButton
import com.example.mobile_mastermind.ui.components.TextClickable
import com.example.mobile_mastermind.ui.components.TextFieldInput

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel()) {
    val uiState = loginViewModel.uiState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp, 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.app_name_first),
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
            Text(
                text = stringResource(R.string.app_name_second),
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldInput(
                value = uiState.username,
                onValueChange = { loginViewModel.onUsernameChanged(it) },
                title = stringResource(id = R.string.login_username),
                placeholder = stringResource(id = R.string.login_username_placeholder),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldInput(
                value = uiState.password,
                onValueChange = { loginViewModel.onPasswordChanged(it) },
                title = stringResource(id = R.string.login_password),
                placeholder = stringResource(id = R.string.login_password_placeholder),
                isPassword = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextClickable(
                stringResource(R.string.login_forgot_password),
                stringResource(R.string.login_forgot_password_clickable),
                "?",
                onClick = { loginViewModel.onForgotPasswordClicked() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(text = stringResource(id = R.string.login_button),
                onClick = { loginViewModel.onLoginClicked() })
        }
        TextClickable(
            stringResource(R.string.login_register_prompt),
            stringResource(R.string.login_register_prompt_clickable),
            onClick = { loginViewModel.onRegisterClicked() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val viewModel = LoginViewModel()
    LoginScreen(loginViewModel = viewModel)
}
