package com.example.mobile_mastermind.ui.components

import androidx.compose.ui.text.input.ImeAction

/**
 * Data class containing all configuration parameters for the TextFieldInput component.
 *
 * @property value The current value of the text field
 * @property title The title to display above the text field
 * @property placeholder The placeholder text to display when the field is empty
 * @property enabled If true, the text field will be interactive. Defaults to true
 * @property isPassword Whether the input should be treated as a password (obscured text). Defaults to false
 * @property imeAction The IME (Keyboard) action to display. Defaults to [ImeAction.Next]
 * @property onValueChange Callback invoked when the text value changes
 * @property onImeAction Callback invoked when the IME action is triggered. Defaults to no action
 */

data class TextFieldInputData(
    val value: String,
    val title: String,
    val placeholder: String,
    val enabled: Boolean = true,
    val isPassword: Boolean = false,
    val imeAction: ImeAction = ImeAction.Next,
    val onValueChange: (String) -> Unit,
    val onImeAction: () -> Unit = {}
)
