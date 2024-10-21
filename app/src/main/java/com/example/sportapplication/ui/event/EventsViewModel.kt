package com.example.sportapplication.ui.event

import androidx.lifecycle.ViewModel
import com.example.sportapplication.database.data.PoiStorage
import com.example.sportapplication.database.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val poiStorage: PoiStorage // Inject the PoiStorage to access event data
) : ViewModel() {

    // Function to get all events from PoiStorage
    fun getEvents(): List<Event> {
        return poiStorage.events
    }
}
