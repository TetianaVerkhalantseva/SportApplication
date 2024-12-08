package com.example.sportapplication.ui.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.database.model.Item
import com.example.sportapplication.repository.model.EventWithQuestsUI

@Composable
fun EventsScreenRoute(
    navigateToSelectedEventScreen: (Long) -> Unit
) {
    // ViewModel to work with event data
    val viewModel: EventViewModel = hiltViewModel()
    val events by viewModel.events.collectAsState()
    val items = viewModel.items



    // Send event data to the screen
    EventsScreen(
        events = events,
        onEventClick = { navigateToSelectedEventScreen(it.id) },
        items = items
    )
}

@Composable
fun EventsScreen(
    events: List<EventWithQuestsUI>,
    onEventClick: (EventWithQuestsUI) -> Unit,
    items: SnapshotStateList<Item>
) {
    LazyEventsColumn(
        events = events,
        onEventClick = onEventClick,
        items = items
    )
}

