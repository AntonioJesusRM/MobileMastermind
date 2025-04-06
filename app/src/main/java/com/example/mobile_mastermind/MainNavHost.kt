package com.example.mobile_mastermind

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile_mastermind.ui.game.GameScreen
import com.example.mobile_mastermind.ui.home.HomeScreen
import com.example.mobile_mastermind.ui.login.LoginScreen
import com.example.mobile_mastermind.ui.profile.ProfileScreen
import com.example.mobile_mastermind.ui.ranking.RankingScreen
import com.example.mobile_mastermind.ui.register.RegisterScreen
import com.example.mobile_mastermind.ui.review.ReviewScreen

@Composable
fun MainNavHost(
    navController: NavHostController, startDestination: String
) {
    val context = LocalContext.current
    BackHandler {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        } else {
            (context as? Activity)?.finishAffinity()
        }
    }
    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        composable(route = Register.route) {
            RegisterScreen(navController)
        }
        composable(route = Login.route) {
            LoginScreen(navController)
        }
        composable(route = Home.route) {
            HomeScreen(navController)
        }
        composable(route = Game.route) {
            GameScreen()
        }
        composable(route = Review.route) {
            ReviewScreen()
        }
        composable(route = Ranking.route) {
            RankingScreen(navController)
        }
        composable(route = Profile.route) {
            ProfileScreen(navController)
        }
    }
}