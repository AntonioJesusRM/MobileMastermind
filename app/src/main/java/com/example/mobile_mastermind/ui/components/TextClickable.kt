package com.example.mobile_mastermind.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mobile_mastermind.R

/**
 * A reusable Composable for displaying text with a clickable part.
 *
 * @param previousText The non-clickable text before the clickable part.
 * @param textClickable The clickable text.
 * @param laterText The non-clickable text after the clickable part (optional).
 * @param onClick The callback to be invoked when the clickable text is clicked.
 */
@Composable
fun TextClickable(
    previousText: String,
    textClickable: String,
    laterText: String? = null,
    onClick: () -> Unit
) {
    Row {
        Text(
            text = previousText,
            fontSize = 15.sp,
            color = colorResource(R.color.black)
        )

        Text(
            text = " $textClickable",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.color_green),
            modifier = Modifier.clickable { onClick() }
        )

        if (!laterText.isNullOrEmpty()) {
            Text(
                text = laterText,
                fontSize = 15.sp,
                color = colorResource(R.color.black)
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
        laterText = "?"
    ) {}
}