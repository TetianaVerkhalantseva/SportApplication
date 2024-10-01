package com.example.sportapplication.ui.introduction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay

@Composable
fun IntroductionScreenRoute(
    navigateToMapScreen: () -> Unit
) {
    val viewModel : IntroductionViewModel = hiltViewModel()

    IntroductionScreen(
        navigateToMapScreen = navigateToMapScreen
    )
}

@Composable
fun IntroductionScreen(
    navigateToMapScreen: () -> Unit
) {
    var progress by remember { mutableStateOf(0f) }

    // Simulere lasting med en forsinkelse fra 0 til 100%. Her kan vi endre med annen logikk senere. Kun demo.
    LaunchedEffect(key1 = true) {
        while (progress < 1f) {
            delay(100) // Øk fremgangen hver 100ms
            progress += 0.05f // Øker med 5% hver gang
        }
        // Når progress = 100%, naviger til neste skjerm (map blir default neste skjerm)
        navigateToMapScreen()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Sport Application")
        Spacer(modifier = Modifier.height(20.dp))
        LinearProgressIndicator(progress = progress)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "${(progress * 100).toInt()}%")
    }
}