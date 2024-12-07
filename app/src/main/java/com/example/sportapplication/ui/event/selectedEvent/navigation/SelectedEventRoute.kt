package com.example.sportapplication.ui.event.selectedEvent.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.event.selectedEvent.SelectedEventRoute
import com.example.sportapplication.ui.map.navigation.navigateToMap

private const val EVENT_ID_ARGUMENT = "event_id"
const val SELECTED_EVENT_ROUTE = "selected_event_route"
const val SELECTED_EVENT_ROUTE_WITH_ARGUMENT = "selected_event_route/{event_id}"

fun NavController.navigateToSelectedEvent(eventId: Long, navOptions: NavOptions? = null) =
    navigate(SELECTED_EVENT_ROUTE.plus("/").plus(eventId), navOptions = navOptions)

fun NavGraphBuilder.selectedEventScreen(
    navHostController: NavHostController,
) {
    composable(
        route = SELECTED_EVENT_ROUTE_WITH_ARGUMENT
    ) { navBackStack ->
        SelectedEventRoute(
            eventId = navBackStack.arguments?.getString(EVENT_ID_ARGUMENT)!!.toLong(),
            navigateToMapScreen = { navHostController.navigateToMap() },
            onBackClick = { navHostController.popBackStack() }
        )
    }
}
