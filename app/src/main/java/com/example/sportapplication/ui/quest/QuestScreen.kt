package com.example.sportapplication.ui.quest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SecondScreenRoute(navigateToMapScreen: () -> Unit) {

    QuestScreen(
        navigateToMapScreen = navigateToMapScreen
    )
}

@Composable
fun QuestScreen(
    navigateToMapScreen: () -> Unit
) {
    Column {
        Text(
            modifier = Modifier.clickable {
                navigateToMapScreen()
            },
            text = "Quest screen"
        )
    }
}