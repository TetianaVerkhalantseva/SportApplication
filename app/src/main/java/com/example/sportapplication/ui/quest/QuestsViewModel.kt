package com.example.sportapplication.ui.quest

import androidx.lifecycle.ViewModel
import com.example.sportapplication.database.data.PoiStorage
import com.example.sportapplication.database.model.EventQuest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel

class QuestsViewModel @Inject constructor(
    private val poiStorage: PoiStorage // Inject the PoiStorage to access quest data
) : ViewModel() {

    // Function to retrieve all quests from PoiStorage
    fun getQuests(): List<EventQuest> {
        return poiStorage.eventQuests
    }

    // Optionally, you can add more functions to handle quest-related logic here
}