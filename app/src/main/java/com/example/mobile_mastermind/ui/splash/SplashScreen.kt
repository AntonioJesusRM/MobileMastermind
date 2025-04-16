package com.example.mobile_mastermind.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobile_mastermind.Home
import com.example.mobile_mastermind.Login
import com.example.mobile_mastermind.R
import com.example.mobile_mastermind.data.session.DataUserSession
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, dataUserSession: DataUserSession) {

    LaunchedEffect(Unit) {
        delay(1500)
        val destination = if (dataUserSession.haveSession()) Home.route else Login.route
        navController.navigate(destination) {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_mobilemastermind),
            contentDescription = "App Logo",
            modifier = Modifier.size(400.dp)
        )
    }
}