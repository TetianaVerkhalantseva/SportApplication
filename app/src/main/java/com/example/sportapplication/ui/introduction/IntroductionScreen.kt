package com.example.sportapplication.ui.introduction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.R
import kotlinx.coroutines.delay

@Composable
fun IntroductionScreenRoute(
    navigateToMapScreen: () -> Unit
) {
    val viewModel: IntroductionViewModel = hiltViewModel()

    IntroductionScreen(
        navigateToMapScreen = navigateToMapScreen
    )
}

@Composable
fun IntroductionScreen(
    navigateToMapScreen: () -> Unit
) {
    var progress by remember { mutableStateOf(0f) }

    // Simulate loading progress
    LaunchedEffect(key1 = true) {
        while (progress < 1f) {
            delay(100)
            progress += 0.05f
        }
        // Navigate to next screen after progress reaches 100%
        navigateToMapScreen()
    }

    // Use theme's background color
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.welcome_to_sport_application),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))


        LinearProgressIndicator(
            progress = progress,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Display progress percentage using theme text style
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.bodyMedium,       // Theme font for body text
            color = MaterialTheme.colorScheme.onBackground     // Ensure good contrast with background
        )
    }
}
