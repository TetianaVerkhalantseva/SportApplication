package com.example.sportapplication.ui.introduction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    // Main layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Welcome message at the top with blue color
        Text(
            text = stringResource(R.string.welcome_to_questabout_2),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(bottom = 24.dp) // Increased spacing below welcome message
        )

        // Upper 50% scrollable text section
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
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp) // Increased spacing below the title
                )

                Text(
                    text = stringResource(R.string.questabout_is_an_app_designed_to_bring_together_gamification_outdoor_activities_and_social_interaction_explore_nature_while_earning_achievements_and_meeting_like_minded_people_the_app_includes_features_like_a_customizable_map_navigation_tools_event_tracking_and_options_to_set_up_personal_quests_users_can_customize_preferences_creating_a_tailored_experience_through_camera_input_the_gameplay_becomes_more_interactive_adding_an_ar_like_experience_to_your_outdoor_adventures_complete_challenges_alone_or_collaborate_with_others_for_group_based_achievements_the_app_tracks_weather_conditions_suggests_hotspots_and_visualizes_your_activity_through_heatmaps_customize_your_avatar_earn_badges_and_keep_up_with_others_through_real_time_communication_keep_the_adventure_alive_with_questabout_s_dynamic_and_social_features_this_application_has_been_carefully_crafted_to_ensure_a_fun_and_engaging_experience_for_users_who_want_to_combine_exercise_with_exploration_and_social_connection_it_s_all_about_getting_outside_moving_and_making_new_connections_track_your_progress_manage_your_quests_and_customize_your_experience_every_step_of_the_way_questabout_makes_sure_every_journey_is_an_adventure_and_every_adventure_brings_new_opportunities_whether_you_are_looking_for_a_peaceful_hike_a_challenging_mountain_climb_or_just_a_walk_in_the_park_questabout_is_your_perfect_companion_stay_motivated_with_rewards_meet_new_friends_and_keep_discovering_new_places_let_the_journey_begin).trimIndent(),
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
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}



