package com.example.mobile_mastermind.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mobile_mastermind.ui.theme.White

@Composable
fun ProgressCircle() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}