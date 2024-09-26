package com.example.sportapplication.ui.quest.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.quest.SecondScreenRoute

const val QUEST_ROUTE = "quest_route"

fun NavController.navigateToQuest(navOptions: NavOptions? = null) =
    navigate(QUEST_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.questScreen(
    navHostController: NavHostController,
    navigateToMapScreen: () -> Unit,
) {
    composable(
        route = QUEST_ROUTE
    ) {
        SecondScreenRoute(
            navigateToMapScreen = navigateToMapScreen
        )
    }
}