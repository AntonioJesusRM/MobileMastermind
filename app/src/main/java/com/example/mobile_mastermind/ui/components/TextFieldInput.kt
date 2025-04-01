package com.example.mobile_mastermind.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
 * @param value The current value of the text field, represented as a [MutableState].
 * @param title The title to display above the text field.
 * @param placeholder The placeholder text to display when the field is empty.
 * @param modifier The modifier to be applied to the text field.
 * @param onValueChange The callback to be invoked when the text value changes.
 * @param isPassword Whether the input should be treated as a password (obscured text). Defaults to false.
 */
@Composable
fun TextFieldInput(
    value: String,
    title: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
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
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldInputPreview() {
    TextFieldInput(
        value = "",
        onValueChange = {},
        title = stringResource(id = R.string.username),
        placeholder = stringResource(id = R.string.login_username_placeholder)
    )
}