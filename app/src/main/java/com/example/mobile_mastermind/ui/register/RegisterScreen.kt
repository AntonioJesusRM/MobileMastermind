package com.example.mobile_mastermind.ui.register

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import coil.compose.AsyncImage
import com.example.mobile_mastermind.Home
import com.example.mobile_mastermind.Login
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.Register
import com.example.mobile_mastermind.ui.components.PrimaryButton
import com.example.mobile_mastermind.ui.components.TextClickable
import com.example.mobile_mastermind.ui.components.TextFieldInput
import com.example.mobile_mastermind.ui.components.TextFieldInputData

@Composable
fun RegisterScreen(
    navController: NavHostController, registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState = registerViewModel.uiState.value
    val registerResult by registerViewModel.registerResult.collectAsState()

    LaunchedEffect(registerResult) {
        when (registerResult) {
            is RegisterResult.Success -> {
                navController.navigate(Home.route, navOptions {
                    popUpTo(Register.route) { inclusive = true }
                })
                registerViewModel.clearRegisterResult()
            }

            is RegisterResult.Error -> {
                registerViewModel.clearRegisterResult()
            }

            else -> Unit
        }
    }
    RegisterBody(navController, uiState, registerViewModel)
}

@Composable
fun RegisterBody(
    navController: NavHostController, uiState: RegisterUiState, registerViewModel: RegisterViewModel
) {
    val context = LocalContext.current
    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            registerViewModel.onImageSelected(it)
        }
    }

    val focusManager = LocalFocusManager.current
    val (focusRequesterEmail, focusRequesterPassword, focusRequesterRepeatPassword) = rememberFocusRequesters()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.register_title),
                style = MaterialTheme.typography.titleLarge
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(11.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (uiState.userImg.isNotEmpty()) {
                        AsyncImage(
                            model = uiState.userImg,
                            contentDescription = stringResource(R.string.user_image_content_description),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.Black, CircleShape)
                                .clickable {
                                    pickMediaLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                })
                    } else {
                        IconButton(
                            onClick = {
                                pickMediaLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                            modifier = Modifier
                                .size(100.dp)
                                .border(1.dp, Color.Black, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Image,
                                contentDescription = stringResource(R.string.user_image_content_description),
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(100.dp)
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Filled.Image,
                        contentDescription = stringResource(R.string.user_image_content_description)
                    )
                    Text(
                        text = stringResource(R.string.register_select_profile_image),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                TextFieldInput(
                    modifier = Modifier.fillMaxWidth(),
                    data = TextFieldInputData(
                        value = uiState.username,
                        onValueChange = { registerViewModel.onUsernameChanged(it) },
                        title = stringResource(id = R.string.username),
                        placeholder = stringResource(id = R.string.login_username_placeholder),
                        enabled = !uiState.isLoading,
                        onImeAction = { focusRequesterEmail.requestFocus() }),
                )
                TextFieldInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequesterEmail),
                    data = TextFieldInputData(
                        value = uiState.email,
                        onValueChange = { registerViewModel.onEmailChanged(it) },
                        title = stringResource(id = R.string.register_email),
                        placeholder = stringResource(id = R.string.register_email),
                        enabled = !uiState.isLoading,
                        onImeAction = { focusRequesterPassword.requestFocus() }),
                )
                TextFieldInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequesterPassword),
                    data = TextFieldInputData(
                        value = uiState.password,
                        onValueChange = { registerViewModel.onPasswordChanged(it) },
                        title = stringResource(id = R.string.password),
                        placeholder = stringResource(id = R.string.password),
                        isPassword = true,
                        enabled = !uiState.isLoading,
                        onImeAction = { focusRequesterRepeatPassword.requestFocus() }),
                )
                TextFieldInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequesterRepeatPassword),
                    data = TextFieldInputData(
                        value = uiState.passwordRepeat,
                        onValueChange = { registerViewModel.onRepeatPasswordChanged(it) },
                        title = stringResource(id = R.string.register_repeat_password),
                        placeholder = stringResource(id = R.string.register_repeat_password),
                        isPassword = true,
                        enabled = !uiState.isLoading,
                        imeAction = ImeAction.Done,
                        onImeAction = {
                            focusManager.submitForm(registerViewModel, context)
                        })
                )
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                } else {
                    PrimaryButton(
                        text = stringResource(id = R.string.register_button),
                        enabled = registerViewModel.isAllFilled(),
                        onClick = {
                            focusManager.submitForm(registerViewModel, context)
                        })
                }
            }
        }
        TextClickable(
            stringResource(R.string.register_login_prompt),
            stringResource(R.string.register_login_prompt_clickable),
            onClick = {
                navController.navigate(Login.route, navOptions {
                    popUpTo(Register.route) { inclusive = true }
                })
                registerViewModel.clearRegisterResult()
            },
            enabled = !uiState.isLoading
        )
    }
}

@Composable
private fun rememberFocusRequesters(): Triple<FocusRequester, FocusRequester, FocusRequester> {
    return remember {
        Triple(
            FocusRequester(), FocusRequester(), FocusRequester()
        )
    }
}

private fun FocusManager.submitForm(
    registerViewModel: RegisterViewModel, context: Context
) {
    clearFocus(force = true)
    registerViewModel.onRegisterClicked(context)
}