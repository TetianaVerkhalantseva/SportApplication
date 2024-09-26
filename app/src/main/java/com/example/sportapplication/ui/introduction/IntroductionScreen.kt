package com.example.sportapplication.ui.introduction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

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
    Column {
        Text(
            modifier = Modifier.clickable {
                navigateToMapScreen()
            },
            text = "Introduction page!"
        )
    }
}
