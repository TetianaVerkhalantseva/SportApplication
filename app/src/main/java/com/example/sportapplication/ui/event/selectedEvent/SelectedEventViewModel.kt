package com.example.sportapplication.ui.event.selectedEvent

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.database.model.Item
import com.example.sportapplication.repository.ItemRepository
import com.example.sportapplication.repository.UserRepository
import com.example.sportapplication.repository.model.EventWithQuestsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedEventViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val itemRepository: ItemRepository

): ViewModel() {

    private val _event = MutableStateFlow<EventWithQuestsUI?>(null)
    val event = _event.asStateFlow()

    var item by mutableStateOf<Item?>(null)

    fun setEventId(eventId: Long?) {
        viewModelScope.launch {
            eventId?.let {
                val event = userRepository.getEventWithQuestsUIById(it)
                _event.emit(event)

                if (event != null) {
                    val itemId = event.rewardItemId
                    if(itemId != null){
                        Log.i("Event reward id", itemId.toString())
                        item = itemRepository.getItemById(itemId)
                    }
                }


            }



        }
    }
}