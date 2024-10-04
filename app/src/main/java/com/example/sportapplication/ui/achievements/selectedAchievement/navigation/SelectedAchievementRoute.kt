package com.example.sportapplication.ui.achievements.selectedAchievement.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.achievements.selectedAchievement.SelectedAchievementRoute

const val SELECTED_ACHIEVEMENT_ROUTE_WITH_ARGUMENTS = "selected_achievement_route/{id}"
const val SELECTED_ACHIEVEMENT_ROUTE = "selected_achievement_route"

fun NavController.navigateToSelectedAchievement(
    id: Long,
    navOptions: NavOptions? = null
) =
    navigate(SELECTED_ACHIEVEMENT_ROUTE.plus("/${id}"), navOptions = navOptions)

fun NavGraphBuilder.selectedAchievementScreen(
    navHostController: NavHostController
) {
    composable(
        route = SELECTED_ACHIEVEMENT_ROUTE_WITH_ARGUMENTS
    ) { backStack ->
        SelectedAchievementRoute(
            achievementId = backStack.arguments?.getString(ArgumentsKeys.ID.key)?.toLong(),
            onBackClick = { navHostController.popBackStack() }
        )
    }
}

enum class ArgumentsKeys(val key: String) {
    ID("id"),
}