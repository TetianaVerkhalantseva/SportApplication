package com.example.sportapplication.ui.achievements.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.database.model.Achievement
import com.example.sportapplication.ui.achievements.AchievementsScreenRoute

const val ACHIEVEMENTS_ROUTE = "achievements_route"

fun NavController.navigateToAchievements(navOptions: NavOptions? = null) =
    navigate(ACHIEVEMENTS_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.achievementsScreen(
    navHostController: NavHostController,
    navigateToSelectedAchievementScreen : (Achievement) -> Unit
) {
    composable(
        route = ACHIEVEMENTS_ROUTE
    ) {
        AchievementsScreenRoute(
            onAchievementClicked = navigateToSelectedAchievementScreen
        )
    }
}