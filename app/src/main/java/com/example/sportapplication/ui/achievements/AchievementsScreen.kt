package com.example.sportapplication.ui.achievements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AchievementsScreenRoute() {

    AchievementsScreen(
    )
}

@Composable
fun AchievementsScreen(
) {
    Column {
        Text(
            modifier = Modifier.clickable {
            },
            text = "ACHIEVEMENTS SCREEN"
        )
    }
}