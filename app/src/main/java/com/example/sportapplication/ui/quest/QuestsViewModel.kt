package com.example.sportapplication.ui.quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class QuestsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _quests = MutableStateFlow<List<Quest>>(emptyList())
    val quests = _quests.asStateFlow()

    init {
        getQuests()
    }

    private fun getQuests() {
        viewModelScope.launch {
            _quests.emit(userRepository.getAllQuestsUI())
        }
    }
}