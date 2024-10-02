package com.example.sportapplication.ui.quest.selectedQuest.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.inventory.navigation.navigateToInventory
import com.example.sportapplication.ui.quest.selectedQuest.SelectedQuestRoute

const val SELECTED_QUEST_ROUTE = "selected_quest_route"

fun NavController.navigateToSelectedQuest(navOptions: NavOptions? = null) =
    navigate(SELECTED_QUEST_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.selectedQuestScreen(
    navHostController: NavHostController,
) {
    composable(
        route = SELECTED_QUEST_ROUTE
    ) {
        SelectedQuestRoute(
            onBackClick = { navHostController.popBackStack() },
            navigateToInventoryScreen = { navHostController.navigateToInventory() }
        )
    }
}