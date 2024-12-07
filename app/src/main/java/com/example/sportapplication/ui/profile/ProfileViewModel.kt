package com.example.sportapplication.ui.profile

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.database.dao.UserDao
import com.example.sportapplication.database.entity.AchievedEvent
import com.example.sportapplication.database.entity.AchievedQuest
import com.example.sportapplication.database.entity.User
import com.example.sportapplication.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDao: UserDao,
    private val userRepository: UserRepository
) : ViewModel() {

    // MutableStateFlow to hold the current nickname. Initially set to "CurrentNickname"
    private val _nickname = MutableStateFlow("CurrentNickname")
    val nickname: StateFlow<String> = _nickname

    private val _userExperience = MutableStateFlow(userRepository.getUserExperience())
    val userExperience = _userExperience.asStateFlow()
    private val _completedQuestsAmount = MutableStateFlow(0)
    val completedQuestsAmount = _completedQuestsAmount.asStateFlow()
    private val _completedEventsAmount = MutableStateFlow(0)
    val completedEventsAmount = _completedEventsAmount.asStateFlow()

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
            user?.let {
                _nickname.value = it.name
            }
        }
        observeStatistics()
    }

    private fun observeStatistics() {
        viewModelScope.launch {
            achievedQuestsLiveData.observeForever(_questsObserver)
            achievedEventsLiveData.observeForever(_eventsObserver)
        }
    }
    // Function to update the nickname. True/false.
    suspend fun updateNickname(newNickname: String): Boolean {
        return try {
            val user = userDao.getUser()
            if (user != null) {
                val updatedUser = user.copy(name = newNickname)
                userDao.updateUser(updatedUser)
            } else {
                val newUser = User(name = newNickname)
                userDao.insertUser(newUser)
            }
            _nickname.value = newNickname
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
