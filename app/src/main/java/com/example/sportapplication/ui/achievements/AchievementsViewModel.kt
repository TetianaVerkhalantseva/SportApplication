package com.example.sportapplication.ui.achievements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.database.data.AchievementType
import com.example.sportapplication.database.model.Achievement
import com.example.sportapplication.repository.AchievementsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val achievementsRepository: AchievementsRepository
): ViewModel() {

    private val _achievements = MutableStateFlow<Map<AchievementType, List<Achievement>>?>(null)
    val achievements = _achievements.asStateFlow()


    init {
        viewModelScope.launch {
            _achievements.emit(achievementsRepository.getUserAchievements())
        }
    }
}