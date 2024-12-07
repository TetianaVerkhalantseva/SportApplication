package com.example.sportapplication.ui.event.selectedEvent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.repository.UserRepository
import com.example.sportapplication.repository.model.EventWithQuestsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedEventViewModel @Inject constructor(
    private val userRepository: UserRepository

): ViewModel() {

    private val _event = MutableStateFlow<EventWithQuestsUI?>(null)
    val event = _event.asStateFlow()

    fun setEventId(eventId: Long?) {
        viewModelScope.launch {
            eventId?.let {
                _event.emit(userRepository.getEventWithQuestsUIById(it))
            }
        }
    }
}