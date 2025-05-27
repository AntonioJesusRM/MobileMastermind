package com.example.mobile_mastermind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.mobile_mastermind.data.session.DataUserSession
import com.example.mobile_mastermind.ui.theme.BackgroundLight
import com.example.mobile_mastermind.ui.theme.MOBILEMASTERMINDTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dataUserSession: DataUserSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MOBILEMASTERMINDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundLight
                ) {
                    MobileMastermind(dataUserSession)
                }
            }
        }
    }
}

@Composable
fun MobileMastermind(dataUserSession: DataUserSession) {
    val navController = rememberNavController()
    MainNavHost(
        navController = navController,
        dataUserSession = dataUserSession,
        startDestination = Splash.route
    )
}