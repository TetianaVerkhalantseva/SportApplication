package com.example.sportapplication.ui.event.selectedEvent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SelectedEventRoute(
    onBackClick: () -> Unit
) {
    SelectedEventScreen(
        onBackClick = onBackClick
    )
}

@Composable
fun SelectedEventScreen(
    onBackClick: () -> Unit
) {
    // Here will be the logic for displaying the screen of the selected event

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "This is the selected event screen. Here will be displaying the selected event")
    }
}
