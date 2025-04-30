package com.example.mobile_mastermind.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mobile_mastermind.Login
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.domain.model.users.CategoryStatsModel
import com.example.mobile_mastermind.ui.components.BottomNav
import com.example.mobile_mastermind.ui.components.ProgressCircle
import com.example.mobile_mastermind.ui.extension.PutImage
import com.example.mobile_mastermind.ui.extension.toComposeColor
import com.example.mobile_mastermind.ui.home.ErrorScreen
import com.example.mobile_mastermind.ui.theme.BackgroundLight
import com.example.mobile_mastermind.ui.theme.GoldLight
import com.example.mobile_mastermind.ui.theme.GreenLight
import com.example.mobile_mastermind.ui.theme.PlaceholderLight
import com.example.mobile_mastermind.ui.theme.RedLight
import com.example.mobile_mastermind.ui.theme.White

@Composable
fun ProfileScreen(
    navController: NavController, profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState = profileViewModel.uiState.value
    val logoutResult by profileViewModel.logoutResult.collectAsState()

    LaunchedEffect(logoutResult) {
        if (logoutResult is LogoutResult.Success) {
            profileViewModel.clearState()
            navController.navigate(Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    when {
        uiState.isLoading -> ProgressCircle()

        uiState.errorMessage != null -> {
            ErrorScreen(message = uiState.errorMessage) {
                navController.navigate(Login.route) {
                    popUpTo(0)
                }
            }
        }

        else -> {
            Scaffold(
                containerColor = Color.Transparent,
                bottomBar = { BottomNav(navController) }) { padding ->
                ProfileBody(
                    uiState = uiState,
                    marginBot = padding.calculateBottomPadding(),
                    onLogoutClick = { profileViewModel.onLogoutClick() })
            }
        }
    }
}

@Composable
private fun ProfileBody(
    uiState: ProfileUiState, marginBot: Dp, onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 25.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProfileCard(
            modifier = Modifier, uiState, onLogoutClick
        )
        Spacer(modifier = Modifier.height(11.dp))
        DataCard(modifier = Modifier, uiState.points, uiState.bestScore, uiState.ranking)
        Spacer(modifier = Modifier.height(13.dp))
        Text(
            text = stringResource(R.string.profile_title_stats),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(15.dp))
        StatsCard(modifier = Modifier, uiState.stats, marginBot)
    }
}

@Composable
private fun StatsCard(
    modifier: Modifier = Modifier,
    stats: List<CategoryStatsModel>,
    marginBot: Dp
) {
    Card(
        modifier = modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RectangleShape
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
                verticalArrangement = Arrangement.spacedBy(13.dp),
                contentPadding = PaddingValues(bottom = marginBot)
            ) {
                items(items = stats) { category ->
                    StatCategoryItem(category = category)
                }
            }
        }
    }
}

@Composable
private fun StatCategoryItem(category: CategoryStatsModel) {
    val stats = listOf(
        StatItem(
            statImg = null,
            title = category.category.name,
            statColor = category.category.color.toComposeColor()
        ),
        StatItem(
            statImg = R.drawable.stat_check_item,
            title = stringResource(R.string.profile_best_score_title),
            statBackground = R.drawable.stat_best_background,
            value = category.bestScore,
            unit = stringResource(R.string.profile_stat_unit),
            statColor = category.category.color.toComposeColor()
        ),
        StatItem(
            statImg = R.drawable.stat_best_question_item,
            title = stringResource(R.string.profile_stat_best_question),
            statBackground = R.drawable.stat_best_background,
            value = category.bestQuestion,
            unit = stringResource(R.string.profile_stat_unit),
            statColor = category.category.color.toComposeColor()
        ),
        StatItem(
            statBackground = R.drawable.stat_total_games_item,
            title = stringResource(R.string.profile_stat_total_games),
            value = category.totalGames,
            unit = "",
            statColor = category.category.color.toComposeColor()
        ),
        StatItem(
            statImg = R.drawable.stat_check_item,
            title = stringResource(R.string.profile_stat_correct_answers),
            statBackground = R.drawable.stat_answer_background,
            value = category.correctAnswers,
            unit = "",
            statColor = category.category.color.toComposeColor()
        ),
        StatItem(
            statImg = R.drawable.stat_incorrect_answer_item,
            title = stringResource(R.string.profile_stat_incorrect_answers),
            statBackground = R.drawable.stat_answer_background,
            value = category.incorrectAnswers,
            unit = "",
            statColor = category.category.color.toComposeColor()
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
            .width(120.dp)
            .height(150.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (stat.statBackground == null) stat.statColor
            else BackgroundLight
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            if (stat.statBackground != null) {
                PutImage(stat.statBackground, stat.statImg, stat.statColor, 50)
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = stat.title,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${stat.value} ${stat.unit}",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
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
            text = label, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center
        )

        Text(
            text = value.toString(), style = MaterialTheme.typography.bodyLarge, color = textColor
        )
    }
}

@Composable
private fun ProfileCard(
    modifier: Modifier = Modifier, uiState: ProfileUiState, onLogoutClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp, 0.dp), colors = CardDefaults.cardColors(
            containerColor = BackgroundLight,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AsyncImage(
                    model = uiState.profileImg,
                    contentDescription = stringResource(R.string.profile_user_content_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = uiState.username, style = MaterialTheme.typography.titleLarge
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(), contentAlignment = Alignment.TopEnd
            ) {
                IconButton(onClick = {
                    onLogoutClick()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = stringResource(R.string.profile_close_session),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}