package com.example.mobile_mastermind.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_mastermind.R

/**
 * A customizable card component for displaying category information.
 *
 * @param title The title of the category.
 * @param category The category type (e.g., "Language").
 * @param questions The number of questions in the category.
 * @param icon A composable lambda for the category icon.
 * @param onClick Callback for when the card is clicked.
 */

@Composable
fun CategoryCard(
    title: String,
    category: String,
    questions: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white),
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                icon()
                Spacer(modifier = Modifier.width(19.dp))
                Column {
                    Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "$category - $questions questions",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = stringResource(R.string.home_icon_arrow_description),
                tint = colorResource(R.color.color_green)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    CategoryCard(
        title = "Kotlin",
        category = "Language",
        questions = "10",
        icon = {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Category Icon",
                tint = colorResource(R.color.color_green)
            )
        },
        onClick = {}
    )
}