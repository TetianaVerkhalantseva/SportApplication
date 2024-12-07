package com.example.sportapplication.ui.profile

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.database.dao.UserDao
import com.example.sportapplication.database.entity.AchievedEvent
import com.example.sportapplication.database.entity.AchievedQuest
import com.example.sportapplication.database.entity.User
import com.example.sportapplication.repository.AchievementsRepository
import com.example.sportapplication.repository.UserRepository
import com.example.sportapplication.ui.settings.AvatarHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDao: UserDao,
    private val userRepository: UserRepository,
    private val achievementsRepository: AchievementsRepository
) : ViewModel() {

    private val _nickname = MutableStateFlow("Player") // Default value
    val nickname: StateFlow<String> get() = _nickname

    private val _avatarId = MutableStateFlow(0) // Default avatar ID
    val avatarId: StateFlow<Int> get() = _avatarId

    // Initialiser brukerdata ved oppstart
    private val _userExperience = MutableStateFlow(userRepository.getUserExperience())
    val userExperience = _userExperience.asStateFlow()
    private val _completedQuestsAmount = MutableStateFlow(0)
    val completedQuestsAmount = _completedQuestsAmount.asStateFlow()
    private val _completedEventsAmount = MutableStateFlow(0)
    val completedEventsAmount = _completedEventsAmount.asStateFlow()
    private val _completedAchievementsAmount = MutableStateFlow(0)
    val completedAchievementsAmount = _completedAchievementsAmount.asStateFlow()
    private val _poiVisitedAmount = MutableStateFlow(0)
    val poiVisitedAmount = _poiVisitedAmount.asStateFlow()

    val achievedQuestsLiveData = userRepository.getAllAchievedQuestsLiveData()
    val _questsObserver = Observer<List<AchievedQuest>> {
        viewModelScope.launch {
            _completedQuestsAmount.emit(it.size)
        }
    }

    val achievedEventsLiveData = userRepository.getAllAchievedEventsLiveData()
    val _eventsObserver = Observer<List<AchievedEvent>> {
        viewModelScope.launch {
            _completedEventsAmount.emit(it.size)
        }
    }


    // Fetch the user data from the database
    init {
        viewModelScope.launch {
            val user = userDao.getUser()
            if (user != null) {
                _nickname.value = user.name
                _avatarId.value = user.avatarId
            } else {
                _nickname.value = "Player"
                _avatarId.value = 0
                userDao.insertUser(User(name = "Player", avatarId = 0))
            }
            // Oppdater AvatarHelper med initial verdier
            AvatarHelper.updateNickname(_nickname.value)
            AvatarHelper.updateAvatar(_avatarId.value)
        }
        observeStatistics()
    }

    private fun observeStatistics() {
        viewModelScope.launch {
            achievedQuestsLiveData.observeForever(_questsObserver)
            achievedEventsLiveData.observeForever(_eventsObserver)

            _completedAchievementsAmount.emit(achievementsRepository.getAmountOfAchievedAchievements())

            val achievedEvents = userRepository.getAllAchievedEvents()
            val achievedQuests = userRepository.getAllAchievedQuests()
            _poiVisitedAmount.emit(
                achievedEvents.sumOf {
                    it.questsIds.size
                }.plus(
                    achievedQuests.size
                )
            )
        }
    }

    // Oppdater brukernavn
    suspend fun updateNickname(newNickname: String): Boolean {
        return try {
            val user = userDao.getUser()
            if (user != null) {
                val updatedUser = user.copy(name = newNickname)
                userDao.updateUser(updatedUser)
            } else {
                val newUser = User(name = newNickname, avatarId = _avatarId.value)
                userDao.insertUser(newUser)
            }
            _nickname.value = newNickname
            AvatarHelper.updateNickname(newNickname) // Update AvatarHelper as well
            true
        } catch (e: Exception) {
            false
        }
    }

    // Oppdater avatar
    suspend fun updateAvatar(newAvatarId: Int): Boolean {
        return try {
            val user = userDao.getUser()
            if (user != null) {
                val updatedUser = user.copy(avatarId = newAvatarId)
                userDao.updateUser(updatedUser)
            } else {
                val newUser = User(name = _nickname.value, avatarId = newAvatarId)
                userDao.insertUser(newUser)
            }
            _avatarId.value = newAvatarId
            AvatarHelper.updateAvatar(newAvatarId) // Update AvatarHelper as well
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun onCleared() {
        removeObservers()
        super.onCleared()
    }

    private fun removeObservers() {
        viewModelScope.launch {
            achievedQuestsLiveData.removeObserver(_questsObserver)
            achievedEventsLiveData.removeObserver(_eventsObserver)
        }
    }
}
