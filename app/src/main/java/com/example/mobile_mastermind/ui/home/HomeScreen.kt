package com.example.mobile_mastermind.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobile_mastermind.R

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onCategoryClick: (String) -> Unit = {}
) {
    val uiState = homeViewModel.uiState.value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(31.dp)
    ) {
        UserInfoSection(
            userName = uiState.userName,
            userImg = uiState.userImg,
            points = uiState.points,
            lastGame = uiState.lastGame
        )
        CategoriesSection(
            categories = uiState.categories, onCategoryClick = onCategoryClick
        )
    }
}

@Composable
private fun UserInfoSection(
    userName: String, userImg: Int, points: Int, lastGame: LastGame
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(31.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(userImg),
                        contentDescription = stringResource(R.string.home_user_image_content_description),
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Column {
                    Text(
                        text = stringResource(R.string.home_greeting_msg),
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp
                    )
                    Text(
                        text = userName, fontWeight = FontWeight.Bold, fontSize = 15.sp
                    )
                }
            }
            Text(
                text = stringResource(R.string.home_user_points, points),
                style = MaterialTheme.typography.titleMedium
            )
        }
        LastGameCard(lastGame = lastGame)
    }
}

@Composable
private fun LastGameCard(lastGame: LastGame) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.color_green)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.size(107.dp), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(lastGame.iconRes),
                    contentDescription = stringResource(R.string.home_user_image_content_description),
                    modifier = Modifier.size(107.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Row {
                Text(
                    text = stringResource(R.string.home_last_game_text),
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    fontSize = 20.sp
                )
                Text(
                    text = " " + stringResource(R.string.home_user_points, lastGame.points),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

        }
    }
}

@Composable
private fun CategoriesSection(
    categories: List<Category>, onCategoryClick: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.home_title_categories),
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
    category: Category, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.white),
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.size(107.dp), contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(category.iconRes),
                        contentDescription = stringResource(R.string.home_category_image_content_description),
                        modifier = Modifier.size(107.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(19.dp))
                Column {
                    Text(text = category.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "${category.type} - ${category.quizCount} questions",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = stringResource(R.string.home_icon_arrow_content_description),
                tint = colorResource(R.color.color_green)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val viewModel = HomeViewModel()
    HomeScreen(homeViewModel = viewModel)
}