package com.example.sportapplication.ui.event.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.event.EventsScreenRoute

const val EVENT_ROUTE = "event_route"

fun NavController.navigateToEvent(navOptions: NavOptions? = null) =
    navigate(EVENT_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.eventScreen(
    navHostController: NavHostController,
    navigateToSelectedEventScreen: (Long) -> Unit
) {
    composable(
        route = EVENT_ROUTE
    ) {
        EventsScreenRoute(
            navigateToSelectedEventScreen = navigateToSelectedEventScreen
        )
    }
}
