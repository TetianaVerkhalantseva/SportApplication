package com.example.sportapplication.ui.quest.selectedQuest

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
class SelectedQuestViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _quest = MutableStateFlow<Quest?>(null)
    val quest = _quest.asStateFlow()


    fun setQuestId(questId: Long?) {
        viewModelScope.launch {
            questId?.let {
                _quest.emit(userRepository.getQuestUIById(questId))
            }
        }
    }

}