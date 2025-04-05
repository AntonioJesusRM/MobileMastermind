package com.example.mobile_mastermind.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobile_mastermind.Home
import com.example.mobile_mastermind.Profile
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.Ranking
import com.example.mobile_mastermind.ui.extension.PutImage
import com.example.mobile_mastermind.ui.theme.GreenLight
import com.example.mobile_mastermind.ui.theme.PlaceholderLight
import com.example.mobile_mastermind.ui.theme.White

@Composable
fun BottomNav(navController: NavController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val items = getBottomNavItems()

    NavigationBar(
        containerColor = White.copy(alpha = 0.8f)
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = { navToScreen(isSelected, navController, item) },
                icon = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(getIconColor(isSelected))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            PutImage(
                                imgBackground = item.icon,
                                color = if (isSelected) White else PlaceholderLight,
                                size = 30,
                                img = null
                            )
                            if (isSelected) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = item.label, color = Color.White
                                )
                            }
                        }
                    }
                },
                label = { Text("") },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

private fun getIconColor(isSelected: Boolean): Color {
    return if (isSelected) GreenLight else Color.Transparent
}

private fun navToScreen(isSelected: Boolean, navController: NavController, item: BottomNavItem) {
    if (!isSelected) {
        navController.navigate(item.route)
    }
}

@Composable
private fun getBottomNavItems(): List<BottomNavItem> = listOf(
    BottomNavItem(
        stringResource(R.string.BottomBar_home_label),
        R.drawable.icon_home_bottom_bar,
        Home.route
    ),
    BottomNavItem(
        stringResource(R.string.BottomBar_ranking_label),
        R.drawable.icon_ranking_bottom_bar,
        Ranking.route
    ),
    BottomNavItem(
        stringResource(R.string.BottomBar_profile_label),
        R.drawable.icon_profile_bottom_bar,
        Profile.route
    )
)

data class BottomNavItem(val label: String, val icon: Int, val route: String)