package com.example.sportapplication.ui.quest.selectedQuest.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.map.navigation.navigateToMap
import com.example.sportapplication.ui.quest.selectedQuest.SelectedQuestRoute

private const val QUEST_ID_ARGUMENT = "quest_id"
const val SELECTED_QUEST_ROUTE = "selected_quest_route"
const val SELECTED_QUEST_ROUTE_WITH_ARGUMENTS = "selected_quest_route/{quest_id}"

fun NavController.navigateToSelectedQuest(questId: Long, navOptions: NavOptions? = null) =
    navigate(SELECTED_QUEST_ROUTE.plus("/").plus(questId), navOptions = navOptions)

fun NavGraphBuilder.selectedQuestScreen(
    navHostController: NavHostController,
) {
    composable(
        route = SELECTED_QUEST_ROUTE_WITH_ARGUMENTS
    ) { navBackStack ->
        SelectedQuestRoute(
            questId = navBackStack.arguments?.getString(QUEST_ID_ARGUMENT)!!.toLong(),
            onBackClick = { navHostController.popBackStack() },
            navigateToMapScreen = { navHostController.navigateToMap() }
        )
    }
}