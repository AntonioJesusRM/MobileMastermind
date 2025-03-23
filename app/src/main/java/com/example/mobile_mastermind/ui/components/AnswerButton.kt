package com.example.mobile_mastermind.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_mastermind.R

/**
 * A customizable button component for displaying answers in a quiz or similar interface.
 *
 * @param answer The text of the answer to display.
 * @param isCorrect Indicates whether the answer is correct. Changes the button's color and icon.
 * @param onClick The callback to be invoked when the button is clicked.
 */
@Composable
fun AnswerButton(
    answer: String,
    isCorrect: Boolean? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = when (isCorrect) {
                true -> colorResource(R.color.color_green)
                false -> colorResource(R.color.color_red)
                else -> Color.White
            },
            contentColor = when (isCorrect) {
                true -> Color.White
                false -> Color.White
                else -> Color.Black
            }
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = answer,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            if (isCorrect != null) {
                if (isCorrect) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Correct Answer",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Incorrect Answer",
                            tint = colorResource(R.color.color_red),
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnswerButtonPreview() {
    Column {
        AnswerButton(
            answer = "val name = 'Kotlin'",
            isCorrect = true,
            onClick = {}
        )
        AnswerButton(
            answer = "var name: String = 'Kotlin'",
            isCorrect = false,
            onClick = {}
        )
        AnswerButton(
            answer = "let String = 'Kotlin'",
            onClick = {}
        )
    }
}