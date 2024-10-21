package com.example.sportapplication.ui.event.selectedEvent.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.event.selectedEvent.SelectedEventRoute

const val SELECTED_EVENT_ROUTE = "selected_event_route"

fun NavController.navigateToSelectedEvent(navOptions: NavOptions? = null) =
    navigate(SELECTED_EVENT_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.selectedEventScreen(
    navHostController: NavHostController,
) {
    composable(
        route = SELECTED_EVENT_ROUTE
    ) {
        SelectedEventRoute(
            onBackClick = { navHostController.popBackStack() }
        )
    }
}
