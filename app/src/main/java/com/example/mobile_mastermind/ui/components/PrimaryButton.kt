package com.example.mobile_mastermind.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.ui.theme.GreenLight
import com.example.mobile_mastermind.ui.theme.White

/**
 * A customizable primary button component for Jetpack Compose.
 *
 * @param text The text to display on the button.
 * @param onClick The callback to be invoked when the button is clicked.
 * @param enabled If `true`, the clickable text will respond to clicks and trigger `onClick`.
 *               If `false`, the text will appear non-interactive.
 * @param modifier The modifier to be applied to the button.
 * @param backgroundColor The background color of the button. Defaults to green.
 * @param contentColor The color of the text inside the button. Defaults to white.
 */

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    backgroundColor: Color = GreenLight,
    contentColor: Color = White
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    PrimaryButton(
        text = stringResource(id = R.string.login_button),
        onClick = {},
        enabled = false,
        modifier = Modifier
    )
}