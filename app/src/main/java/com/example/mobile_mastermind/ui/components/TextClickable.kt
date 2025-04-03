package com.example.mobile_mastermind.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.ui.theme.GreenLight

/**
 * A reusable Composable for displaying text with a clickable part.
 *
 * @param previousText The non-clickable text before the clickable part.
 * @param textClickable The clickable text.
 * @param enabled If `true`, the clickable text will respond to clicks and trigger `onClick`.
 *               If `false`, the text will appear non-interactive.
 * @param laterText The non-clickable text after the clickable part (optional).
 * @param onClick The callback to be invoked when the clickable text is clicked.
 */
@Composable
fun TextClickable(
    previousText: String,
    textClickable: String,
    enabled: Boolean = true,
    laterText: String? = null,
    onClick: () -> Unit
) {
    Row {
        Text(
            text = previousText,
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = " $textClickable",
            style = MaterialTheme.typography.bodyLarge,
            color = GreenLight,
            modifier = if (enabled) Modifier.clickable { onClick() } else Modifier
        )

        if (!laterText.isNullOrEmpty()) {
            Text(
                text = laterText,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextClickablePreview() {
    TextClickable(
        previousText = stringResource(id = R.string.login_forgot_password),
        textClickable = stringResource(id = R.string.login_forgot_password_clickable),
        laterText = "?",
        enabled = false
    ) {}
}