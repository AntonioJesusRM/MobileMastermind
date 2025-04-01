package com.example.mobile_mastermind.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.ui.components.PrimaryButton
import com.example.mobile_mastermind.ui.components.TextClickable
import com.example.mobile_mastermind.ui.components.TextFieldInput
import com.example.mobile_mastermind.ui.theme.MOBILEMASTERMINDTheme

@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel = hiltViewModel()) {
    val uiState = registerViewModel.uiState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.register_title),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(30.dp))
            TextFieldInput(
                value = uiState.username,
                onValueChange = { registerViewModel.onUsernameChanged(it) },
                title = stringResource(id = R.string.username),
                placeholder = stringResource(id = R.string.login_username_placeholder),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(11.dp))
            TextFieldInput(
                value = uiState.password,
                onValueChange = { registerViewModel.onEmailChanged(it) },
                title = stringResource(id = R.string.register_email),
                placeholder = stringResource(id = R.string.register_email)
            )
            Spacer(modifier = Modifier.height(11.dp))
            TextFieldInput(
                value = uiState.password,
                onValueChange = { registerViewModel.onPasswordChanged(it) },
                title = stringResource(id = R.string.password),
                placeholder = stringResource(id = R.string.password),
                isPassword = true
            )
            Spacer(modifier = Modifier.height(11.dp))
            TextFieldInput(
                value = uiState.password,
                onValueChange = { registerViewModel.onRepeatPasswordChanged(it) },
                title = stringResource(id = R.string.register_repeat_password),
                placeholder = stringResource(id = R.string.register_repeat_password),
                isPassword = true
            )
            Spacer(modifier = Modifier.height(11.dp))
            PrimaryButton(
                text = stringResource(id = R.string.register_button),
                onClick = { registerViewModel.onRegisterClicked() })
        }
        TextClickable(
            stringResource(R.string.register_login_prompt),
            stringResource(R.string.register_login_prompt_clickable),
            onClick = { registerViewModel.onLoginClicked() })
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MOBILEMASTERMINDTheme {
        val viewModel = RegisterViewModel()
        RegisterScreen(registerViewModel = viewModel)
    }
}