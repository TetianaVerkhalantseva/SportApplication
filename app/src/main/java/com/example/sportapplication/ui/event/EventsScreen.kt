package com.example.sportapplication.ui.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.repository.model.EventWithQuestsUI

@Composable
fun EventsScreenRoute(
    navigateToSelectedEventScreen: (Long) -> Unit
) {
    // ViewModel to work with event data
    val viewModel: EventViewModel = hiltViewModel()
    val events by viewModel.events.collectAsState()

    // Send event data to the screen
    EventsScreen(
        events = events,
        onEventClick = { navigateToSelectedEventScreen(it.id) }
    )
}

@Composable
fun EventsScreen(
    events: List<EventWithQuestsUI>,
    onEventClick: (EventWithQuestsUI) -> Unit
) {
    LazyEventsColumn(
        events = events,
        onEventClick = onEventClick
    )
}

