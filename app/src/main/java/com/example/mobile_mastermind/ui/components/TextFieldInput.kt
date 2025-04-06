package com.example.mobile_mastermind.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.ui.theme.PlaceholderLight
import com.example.mobile_mastermind.ui.theme.White

/**
 * A customizable text input field component for Jetpack Compose.
 *
 * @param modifier The modifier to be applied to the text field
 * @param data Contains all the configuration and callbacks for the text field
 */

@Composable
fun TextFieldInput(
    modifier: Modifier = Modifier, data: TextFieldInputData
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = data.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = data.value,
            onValueChange = data.onValueChange,
            enabled = data.enabled,
            placeholder = {
                Text(
                    text = data.placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = PlaceholderLight
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            visualTransformation = if (data.isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = data.imeAction),
            keyboardActions = KeyboardActions(onAny = {
                keyboardController?.hide()
                data.onImeAction()
            })
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldInputPreview() {
    val textFieldInputData = TextFieldInputData(
        value = "",
        onValueChange = {},
        title = stringResource(id = R.string.username),
        enabled = true,
        placeholder = stringResource(id = R.string.login_username_placeholder)
    )
    TextFieldInput(data = textFieldInputData)
}