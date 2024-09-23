package com.example.sportapplication.ui.second

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SecondScreenRoute(navigateToMainScreen: () -> Unit) {

    SecondScreen(
        navigateToMainScreen = navigateToMainScreen
    )
}

@Composable
fun SecondScreen(
    navigateToMainScreen: () -> Unit
) {
    Column {
        Text(
            modifier = Modifier.clickable {
                navigateToMainScreen()
            },
            text = "SECONDSCREEN"
        )
    }
}