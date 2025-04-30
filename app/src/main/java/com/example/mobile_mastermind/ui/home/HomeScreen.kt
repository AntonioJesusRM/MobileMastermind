package com.example.mobile_mastermind.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mobile_mastermind.Game
import com.example.mobile_mastermind.Login
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.domain.model.game.CategoryModel
import com.example.mobile_mastermind.ui.components.BottomNav
import com.example.mobile_mastermind.ui.components.ProgressCircle
import com.example.mobile_mastermind.ui.extension.TAG
import com.example.mobile_mastermind.ui.theme.Black
import com.example.mobile_mastermind.ui.theme.GreenLight
import com.example.mobile_mastermind.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState = homeViewModel.uiState.value

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
                HomeBody(
                    navController = navController,
                    uiState = uiState,
                    marginBot = padding.calculateBottomPadding()
                )
            }
        }
    }
}

@Composable
private fun HomeBody(navController: NavController, uiState: HomeUiState, marginBot: Dp) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(31.dp)
    ) {
        UserInfoSection(
            userName = uiState.userName,
            userImg = uiState.userImg,
            points = uiState.points,
            lastGame = uiState.lastGame
        )
        CategoriesSection(
            categories = uiState.categories, marginBot, onCategoryClick = { categoryId ->
                navController.currentBackStackEntry?.savedStateHandle?.set("category", categoryId)
                navController.navigate(Game.route)
            }
        )
    }
}

@Composable
private fun UserInfoSection(
    userName: String, userImg: String, points: Int, lastGame: LastGame
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(31.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 38.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Log.d(TAG, "%> imagen de usuario: $userImg")
                AsyncImage(
                    model = userImg,
                    contentDescription = stringResource(R.string.user_image_content_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
                Column {
                    Text(
                        text = stringResource(R.string.home_greeting_msg),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = userName, style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Text(
                text = stringResource(R.string.home_user_points, points),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        LastGameCard(lastGame = lastGame)
    }
}

@Composable
private fun LastGameCard(lastGame: LastGame) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = GreenLight
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.size(80.dp), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(lastGame.iconRes),
                    contentDescription = stringResource(R.string.user_image_content_description),
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.home_last_game_text),
                    color = White,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = " " + stringResource(R.string.home_user_points, lastGame.points),
                    color = White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }
    }
}

@Composable
private fun CategoriesSection(
    categories: List<CategoryModel>, marginBot: Dp, onCategoryClick: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.home_title_categories),
            style = MaterialTheme.typography.bodyLarge
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = marginBot)
        ) {
            items(items = categories) { category ->
                CategoryCard(
                    category = category, onClick = { onCategoryClick(category.id) })
            }
        }
    }
}

@Composable
private fun CategoryCard(
    category: CategoryModel, onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = White,
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(19.dp)
            ) {
                Box(
                    modifier = Modifier.size(107.dp), contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = category.categoryImg,
                        contentDescription = stringResource(R.string.home_category_image_content_description),
                        modifier = Modifier.size(107.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column {
                    Text(
                        text = category.name, style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(
                            R.string.home_category_number_question,
                            category.type,
                            category.numberQuestions
                        ), style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = stringResource(R.string.home_icon_arrow_content_description),
                tint = GreenLight
            )
        }
    }
}

@Composable
fun ErrorScreen(message: String?, onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1500)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White), contentAlignment = Alignment.Center
    ) {
        if (message != null) {
            Text(
                text = message, color = Black, style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}