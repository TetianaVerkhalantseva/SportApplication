package com.example.sportapplication.ui.event

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
class EventViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _events = MutableStateFlow<List<EventWithQuestsUI>>(emptyList())
    val events = _events.asStateFlow()

    init {
        viewModelScope.launch {
            _events.emit(userRepository.getAllEventsWithQuestsUI())
        }
    }
}
