package com.example.mobile_mastermind.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.ui.theme.BackgroundLight
import com.example.mobile_mastermind.ui.theme.GoldLight
import com.example.mobile_mastermind.ui.theme.GreenLight
import com.example.mobile_mastermind.ui.theme.MOBILEMASTERMINDTheme
import com.example.mobile_mastermind.ui.theme.PlaceholderLight
import com.example.mobile_mastermind.ui.theme.RedLight
import com.example.mobile_mastermind.ui.theme.White

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {
    val uiState = profileViewModel.uiState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileCard(modifier = Modifier, uiState.profileImg, uiState.name, uiState.email)
        Spacer(modifier = Modifier.height(11.dp))
        DataCard(modifier = Modifier, uiState.points, uiState.bestScore, uiState.ranking)
        Spacer(modifier = Modifier.height(13.dp))
        Text(
            text = stringResource(R.string.profile_title_stats),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(15.dp))
        StatsCard(modifier = Modifier, uiState.stats)
    }
}

@Composable
private fun StatsCard(modifier: Modifier = Modifier, stats: List<CategoryStats>) {
    Card(
        modifier = modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        if (stats.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.profile_empty_stats),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                items(items = stats) { category ->
                    StatCategoryItem(
                        category = category
                    )
                }
            }
        }
    }
}

@Composable
private fun StatCategoryItem(category: CategoryStats) {
    val stats = listOf(
        StatItem(statImg = null, title = category.title, statColor = category.colorCategory),
        StatItem(
            statImg = R.drawable.stat_check_item,
            title = stringResource(R.string.profile_stat_best_score),
            statBackground = R.drawable.stat_best_background,
            value = category.bestScore,
            unit = stringResource(R.string.profile_stat_unit),
            statColor = category.colorCategory
        ),
        StatItem(
            statImg = R.drawable.stat_best_question_item,
            title = stringResource(R.string.profile_stat_best_question),
            statBackground = R.drawable.stat_best_background,
            value = category.bestQuestion,
            unit = stringResource(R.string.profile_stat_unit),
            statColor = category.colorCategory
        ),
        StatItem(
            statBackground = R.drawable.stat_total_games_item,
            title = stringResource(R.string.profile_stat_total_games),
            value = category.totalGames,
            unit = "",
            statColor = category.colorCategory
        ),
        StatItem(
            statImg = R.drawable.stat_check_item,
            title = stringResource(R.string.profile_stat_correct_answers),
            statBackground = R.drawable.stat_answer_background,
            value = category.correctAnswers,
            unit = "",
            statColor = category.colorCategory
        ),
        StatItem(
            statImg = R.drawable.stat_incorrect_answer_item,
            title = stringResource(R.string.profile_stat_incorrect_answers),
            statBackground = R.drawable.stat_answer_background,
            value = category.incorrectAnswers,
            unit = "",
            statColor = category.colorCategory
        )
    )
    LazyRow(horizontalArrangement = Arrangement.spacedBy(13.dp)) {
        items(items = stats) { stat ->
            StatCard(stat = stat)
        }
    }
}

@Composable
private fun StatCard(stat: StatItem) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(170.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (stat.statBackground == null) stat.statColor
            else BackgroundLight
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (stat.statBackground != null) {
                PutImage(stat.statBackground, stat.statImg, stat.statColor)
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = stat.title,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${stat.value} ${stat.unit}",
                    style = MaterialTheme.typography.bodyLarge,
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.ranking_category_title, stat.title),
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun PutImage(
    imgBackground: Int, img: Int?, color: Color
) {
    Box(modifier = Modifier.size(60.dp)) {
        Image(
            painter = painterResource(imgBackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(color, BlendMode.SrcIn)
        )

        img?.let {
            Image(
                painter = painterResource(it),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun DataCard(
    modifier: Modifier = Modifier, points: Int, bestScore: Int, ranking: Int
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileStatItem(
                Modifier.weight(1f),
                stringResource(R.string.profile_points_title),
                points,
                R.drawable.points_icon,
                RedLight
            )
            DividerBox()
            ProfileStatItem(
                Modifier.weight(1f),
                stringResource(R.string.profile_best_score_title),
                bestScore,
                R.drawable.best_score_icon,
                GreenLight
            )
            DividerBox()
            ProfileStatItem(
                Modifier.weight(1f),
                stringResource(R.string.profile_ranking_title),
                ranking,
                R.drawable.ranking_icon,
                GoldLight
            )
        }
    }
}

@Composable
private fun DividerBox() {
    Box(
        modifier = Modifier
            .height(40.dp)
            .width(1.dp)
            .background(PlaceholderLight)
    )
}

@Composable
private fun ProfileStatItem(
    modifier: Modifier = Modifier, label: String, value: Int, iconRes: Int, textColor: Color
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.size(30.dp), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = textColor
        )
    }
}

@Composable
private fun ProfileCard(
    modifier: Modifier = Modifier, userImg: Int?, userName: String, userEmail: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp, 0.dp), colors = CardDefaults.cardColors(
            containerColor = BackgroundLight,
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.align(Alignment.Center)
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (userImg != null) {
                        Image(
                            painter = painterResource(userImg),
                            contentDescription = stringResource(R.string.profile_user_content_description),
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(11.dp))
                    Text(
                        text = userName, style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = userEmail, style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MOBILEMASTERMINDTheme {
        val viewModel = ProfileViewModel()
        ProfileScreen(profileViewModel = viewModel)
    }
}