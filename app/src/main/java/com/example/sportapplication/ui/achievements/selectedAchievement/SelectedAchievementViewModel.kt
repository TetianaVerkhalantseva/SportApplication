package com.example.sportapplication.ui.achievements.selectedAchievement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.database.model.Achievement
import com.example.sportapplication.repository.AchievementsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedAchievementViewModel @Inject constructor(
    private val achievementsRepository: AchievementsRepository
): ViewModel() {

    private val _selectedAchievement = MutableStateFlow<Achievement?>(null)
    val selectedAchievement = _selectedAchievement.asStateFlow()

    fun getAchievementById(achievementId: Long?) {
        viewModelScope.launch {
            achievementId?.let {
                _selectedAchievement.emit(achievementsRepository.getAchievementById(achievementId))
            }
        }
    }
}