package com.example.sportapplication.ui.map.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.event.selectedEvent.navigation.navigateToSelectedEvent
import com.example.sportapplication.ui.map.MapScreenRoute
import com.example.sportapplication.ui.quest.selectedQuest.navigation.navigateToSelectedQuest

const val MAP_ROUTE = "map_route"

fun NavController.navigateToMap(navOptions: NavOptions? = null) =
    navigate(MAP_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.mapRoute(
    navHostController: NavHostController,
) {
    composable(
        route = MAP_ROUTE
    ) {
        MapScreenRoute(
            navigateToSelectedMarkerQuestScreen = { navHostController.navigateToSelectedQuest() },
            navigateToSelectedMarkerEventScreen = { navHostController.navigateToSelectedEvent() },
            //navigateToMainScreen = { navHostController.navigateToMain() }
        )
    }
}