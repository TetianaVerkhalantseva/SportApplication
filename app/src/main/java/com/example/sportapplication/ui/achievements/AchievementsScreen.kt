package com.example.sportapplication.ui.achievements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.database.data.AchievementType
import com.example.sportapplication.repository.model.AchievementUI

@Composable
fun AchievementsScreenRoute() {
    val viewModel: AchievementsViewModel = hiltViewModel()
    val achievements by viewModel.achievements.collectAsState()

    AchievementsScreen(
        achievements = achievements
    )
}

@Composable
fun AchievementsScreen(
    achievements: List<Pair<AchievementType, List<AchievementUI>>>?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        achievements?.forEach { achievementPair ->
            Column(modifier = Modifier.padding(8.dp)) {
                // Achievement type heading
                Text(
                    text = achievementPair.first.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                AchievementPanel(
                    achievements = achievementPair.second
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
