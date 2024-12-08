package com.example.sportapplication.ui.introduction

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.R
import kotlinx.coroutines.delay

@Composable
fun IntroductionScreenRoute(
    modifier: Modifier,
    navigateToMapScreen: () -> Unit
) {
    val viewModel: IntroductionViewModel = hiltViewModel()

    IntroductionScreen(
        modifier = modifier,
        navigateToMapScreen = navigateToMapScreen
    )
}

@Composable
fun IntroductionScreen(
    modifier: Modifier,
    navigateToMapScreen: () -> Unit
) {
    var progress by remember { mutableStateOf(0f) }

    // Simulate loading progress
    LaunchedEffect(key1 = true) {
        while (progress < 1f) {
            delay(100)
            progress += 0.05f
        }
    }

    // Main layout
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Welcome message at the top
        Text(
            text = stringResource(R.string.welcome_to_questabout_2),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .padding(bottom = 24.dp) // Increased spacing below welcome message
        )

        // Upper scrollable text section
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.about_questabout),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(bottom = 16.dp) // Increased spacing below the title
                )

                Text(
                    text = stringResource(R.string.questabout_is_an_app_designed_to_bring_together_gamification_outdoor_activities_and_social_interaction_explore_nature_while_earning_achievements_and_meeting_like_minded_people_the_app_includes_features_like_a_customizable_map_navigation_tools_event_tracking_and_options_to_set_up_personal_quests_users_can_customize_preferences_creating_a_tailored_experience),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // Lower section with loading bar and percentage
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (progress < 1f) {
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Display progress percentage
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            else {
                OutlinedButton(
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.tertiary),
                    shape = RoundedCornerShape(20.dp),
                    onClick = { navigateToMapScreen() },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.got_it),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        }
    }
}