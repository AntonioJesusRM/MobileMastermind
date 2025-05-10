package com.example.mobile_mastermind.ui.ranking

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.mobile_mastermind.domain.model.ranking.GetRankingModel
import com.example.mobile_mastermind.ui.components.BottomNav
import com.example.mobile_mastermind.ui.components.ProgressCircle
import com.example.mobile_mastermind.ui.components.ShowEmptyList
import com.example.mobile_mastermind.ui.home.ErrorScreen
import com.example.mobile_mastermind.ui.theme.Black
import com.example.mobile_mastermind.ui.theme.GreenLight
import com.example.mobile_mastermind.ui.theme.White

@Composable
fun RankingScreen(
    navController: NavController, rankingViewModel: RankingViewModel = hiltViewModel()
) {
    val uiState = rankingViewModel.uiState.value
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
                if (uiState.globalRankings.isEmpty()) {
                    ShowEmptyList(
                        modifier = Modifier.padding(padding),
                        text = stringResource(R.string.ranking_empty)
                    )
                } else {
                    RankingBody(
                        uiState = uiState, marginBot = padding.calculateBottomPadding()
                    )
                }
            }
        }
    }

}

@Composable
fun RankingBody(marginBot: Dp, uiState: RankingUiState) {
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.globalRankings.isNotEmpty(), uiState.myPosition) {
        if (uiState.myPosition > 6) {
            val targetIndex = uiState.myPosition - 6
            listState.animateScrollToItem(
                index = targetIndex
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 42.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.ranking_title),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(22.dp))

        TopRankingCard(uiState.globalRankings.take(3), uiState.myPosition)

        Spacer(modifier = Modifier.height(14.dp))

        LazyColumn(
            contentPadding = PaddingValues(bottom = marginBot),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(items = uiState.globalRankings.drop(3)) { item ->
                RankingCard(
                    imageRes = item.user.image,
                    position = uiState.globalRankings.indexOf(item) + 1,
                    playerName = item.user.name,
                    score = item.totalScore,
                    yourPosition = uiState.myPosition == uiState.globalRankings.indexOf(item) + 1
                )
            }
        }
    }
}

@Composable
fun TopRankingCard(
    topRankings: List<GetRankingModel>, yourPosition: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {
                listOf(1, 0, 2).forEach { index ->
                    if (index < topRankings.size) {
                        val user = topRankings[index].user
                        val position = index + 1
                        TopRankingItem(
                            imgUser = user.image,
                            position = position,
                            name = if (yourPosition == position) stringResource(R.string.ranking_your_position)
                            else user.name,
                            points = topRankings[index].totalScore
                        )
                    } else {
                        Spacer(modifier = Modifier.width(100.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TopRankingItem(
    imgUser: String, position: Int, name: String, points: Int, modifier: Modifier = Modifier
) {
    val size = if (position == 1) 150.dp else 100.dp
    val sizeMedalContainer = if (position == 1) 45.dp else 35.dp
    val sizeMedal = if (position == 1) 25.dp else 18.dp
    val backgroundColor = getBackgroundColor(position)

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (position == 1) {
            CrownIcon()
            Spacer(modifier = Modifier.height(5.dp))
        }

        Box(
            modifier = Modifier.size(size), contentAlignment = Alignment.Center
        ) {
            ProfileImage(imgUser, size, backgroundColor)
            MedalBadge(position, sizeMedalContainer, sizeMedal, backgroundColor)
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = name, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(R.string.ranking_points, points),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ProfileImage(imgUser: String, size: Dp, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imgUser,
            contentDescription = stringResource(R.string.ranking_profile_image_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
        )
    }
}

@Composable
private fun MedalBadge(position: Int, sizeContainer: Dp, sizeMedal: Dp, backgroundColor: Color) {
    val imageMedal = when (position) {
        1 -> R.drawable.medalla_primer_puesto
        2 -> R.drawable.medalla_segundo_puesto
        3 -> R.drawable.medalla_tercer_puesto
        else -> R.drawable.medalla_primer_puesto
    }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .size(sizeContainer)
                .offset(y = sizeMedal / 2)
                .background(backgroundColor, CircleShape), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(imageMedal), contentDescription = stringResource(
                    R.string.ranking_medal_content_description, position
                ), modifier = Modifier.size(sizeMedal), contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun CrownIcon() {
    Image(
        painter = painterResource(R.drawable.corona),
        contentDescription = stringResource(R.string.ranking_crown_content_description),
        modifier = Modifier.size(30.dp),
        contentScale = ContentScale.Crop
    )
}

private fun getBackgroundColor(position: Int): Color {
    return when (position) {
        1 -> Color(0xFFFFD700)
        2 -> Color(0xFFC0C0C0)
        3 -> Color(0xFFCD7F32)
        else -> Color.Gray
    }
}

@Composable
private fun RankingCard(
    imageRes: String, position: Int, playerName: String, score: Int, yourPosition: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (yourPosition) GreenLight else White,
            contentColor = if (yourPosition) White else Black,
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
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.width(30.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                ProfileImage(imgUser = imageRes, size = 40.dp, backgroundColor = White)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = if (yourPosition) stringResource(R.string.ranking_your_position) else playerName,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Text(
                text = stringResource(R.string.ranking_points, score),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}