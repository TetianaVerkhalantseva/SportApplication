package com.example.sportapplication.ui.event

import androidx.compose.runtime.mutableStateListOf
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
class EventViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val itemRepository: ItemRepository
) : ViewModel() {

    private val _events = MutableStateFlow<List<EventWithQuestsUI>>(emptyList())
    val events = _events.asStateFlow()

    val items = mutableStateListOf<Item>()

    init {
        viewModelScope.launch {
            _events.emit(userRepository.getAllEventsWithQuestsUI())

            items.clear()
            itemRepository.getAllItems().forEach { items.add(it) }
        }
    }
}
