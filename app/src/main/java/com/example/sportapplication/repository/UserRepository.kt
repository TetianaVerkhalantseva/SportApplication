package com.example.sportapplication.repository

import androidx.lifecycle.LiveData
import com.example.sportapplication.database.dao.AchievedEventsDao
import com.example.sportapplication.database.dao.AchievedQuestsDao
import com.example.sportapplication.database.dao.UserDao
import com.example.sportapplication.database.entity.AchievedEvent
import com.example.sportapplication.database.entity.AchievedQuest
import com.example.sportapplication.database.entity.User
import com.example.sportapplication.database.model.EventResponseBody
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.database.sharedPreferences.AppSharedPreferences
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val achievedEventsDao: AchievedEventsDao,
    private val achievedQuestsDao: AchievedQuestsDao,
    private val poiRepository: PoiRepository,
    private val prefs: AppSharedPreferences
){

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    fun getUserExperience() = prefs.userExperience

    suspend fun insertAchievedEvent(eventId: String) {
        achievedEventsDao.insert(AchievedEvent(id = eventId))
    }

    fun getAllAchievedEventsLiveData(): LiveData<List<AchievedEvent>> =
        achievedEventsDao.getAllLiveData()

    suspend fun getAllAchievedEvents(): List<AchievedEvent> =
        achievedEventsDao.getAll()

    suspend fun getAllNotAchievedEvents(): List<EventResponseBody> {
        val events = poiRepository.getEvents()
        val achievedEvents = achievedEventsDao.getAll()
        return events.filter { event ->
            achievedEvents.find { it.id == event.id.toString() } == null
        }
    }

    suspend fun insertAchievedQuest(questId: Long) {
        achievedQuestsDao.insert(AchievedQuest(id = questId))
    }

    fun getAllAchievedQuestsLiveData() = achievedQuestsDao.getAllLiveData()

    suspend fun getAllAchievedQuests() = achievedQuestsDao.getAll()

    suspend fun getAllNotAchievedQuests(): List<Quest> {
        val quests = poiRepository.getQuests()
        val achievedQuests = achievedQuestsDao.getAll()
        return quests.filter { quest ->
            achievedQuests.find { it.id == quest.id } == null
        }
    }
}