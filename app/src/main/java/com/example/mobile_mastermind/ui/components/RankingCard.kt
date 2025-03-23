package com.example.mobile_mastermind.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_mastermind.R

/**
 * A reusable Composable component that displays a player's ranking information.
 *
 * @param imageRes The resource ID of the player's profile image.
 * @param position The player's position in the ranking.
 * @param playerName The name of the player.
 * @param score The score achieved by the player.
 */
@Composable
fun RankingCard(
    imageRes: Int,
    position: Int,
    playerName: String,
    score: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white),
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$position",
                    fontSize = 20.sp,
                    modifier = Modifier.width(30.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = stringResource(R.string.ranking_profile_image_description),
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = playerName,
                    fontSize = 20.sp
                )
            }

            Text(
                text = "$score puntos",
                fontSize = 15.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RankingCardPreview() {
    RankingCard(
        imageRes = R.drawable.ic_launcher_foreground,
        position = 11,
        playerName = "Clara",
        score = 560
    )
}