package com.example.sportapplication.ui.event

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.database.model.Event

@Composable
fun EventsScreenRoute(
    navigateToSelectedEventScreen: (Long) -> Unit
) {
    // ViewModel to work with event data
    val viewModel: EventViewModel = hiltViewModel()

    // Send event data to the screen
    EventsScreen(
        events = viewModel.getEvents(),
        onEventClick = { navigateToSelectedEventScreen(it.id) }
    )
}

@Composable
fun EventsScreen(
    events: List<Event>,
    onEventClick: (Event) -> Unit
) {
    // Here will be the logic for displaying the list of events
}
