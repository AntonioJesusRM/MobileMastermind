package com.example.mobile_mastermind.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_mastermind.R

/**
 * A customizable card component for displaying a question with an optional image.
 *
 * @param question The text of the question to display.
 * @param imageRes The optional resource ID of the image to display above the question.
 */
@Composable
fun QuestionCard(
    question: String,
    imageRes: Int? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            imageRes?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Question Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp)
                )
            }
            Text(text = question, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionCardPreview() {
    QuestionCard(
        question = "¿Question?",
        imageRes = R.drawable.ic_launcher_foreground
    )
}