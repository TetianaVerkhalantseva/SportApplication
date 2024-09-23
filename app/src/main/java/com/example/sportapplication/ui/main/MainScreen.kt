package com.example.sportapplication.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreenRoute(
    navigateToSecondScreen: () -> Unit
) {
    val viewModel : MainViewModel = hiltViewModel()

    MainScreen(
        navigateToSecondScreen = navigateToSecondScreen
    )
}

@Composable
fun MainScreen(
    navigateToSecondScreen: () -> Unit
) {
    Column {
        Text(
            modifier = Modifier.clickable {
                navigateToSecondScreen()
            },
            text = "MYMAINSCREEN!"
        )
    }
}
