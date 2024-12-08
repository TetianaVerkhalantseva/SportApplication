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
import com.example.sportapplication.mapper.toEventWithQuestsUI
import com.example.sportapplication.repository.model.EventWithQuestsUI
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

    suspend fun getAllAchievedEventsEntity(): List<AchievedEvent> =
        achievedEventsDao.getAll()

    suspend fun getAllAchievedEvents(): List<EventResponseBody> {
        val events = poiRepository.getEvents()
        val achievedEvents = achievedEventsDao.getAll()
        return events.filter { event ->
            achievedEvents.find { it.id == event.id.toString() } != null
        }
    }

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

    suspend fun getAllAchievedQuestsEntity() = achievedQuestsDao.getAll()

    suspend fun getAllAchievedQuests(): List<Quest> {
        val quests = poiRepository.getQuests()
        val achievedQuests = achievedQuestsDao.getAll()
        return quests.filter { quest ->
            achievedQuests.find { it.id == quest.id } != null
        }
    }

    suspend fun getAllNotAchievedQuests(): List<Quest> {
        val quests = poiRepository.getQuests()
        val achievedQuests = achievedQuestsDao.getAll()
        return quests.filter { quest ->
            achievedQuests.find { it.id == quest.id } == null
        }
    }

    suspend fun getAllEventsWithQuestsUI(): List<EventWithQuestsUI> {
        val events = poiRepository.getEvents()
        val achievedEvents = achievedEventsDao.getAll()
        val allEventsQuests = poiRepository.getEventsQuests()
        return events.map { event ->
            val eventsQuests = allEventsQuests.filter {
                event.questsIds.contains(it.id)
            }
            event.toEventWithQuestsUI(
                quests = eventsQuests,
                isCompleted = achievedEvents.find { it.id == event.id.toString() } != null
            )
        }
    }

    suspend fun getEventWithQuestsUIById(eventId: Long): EventWithQuestsUI? =
        getAllEventsWithQuestsUI().find { it.id == eventId }

    suspend fun getAllQuestsUI(): List<Quest> {
        val completedQuests = achievedQuestsDao.getAll().map { it.id }
        val quests = poiRepository.getQuests()

        return quests.map {
            it.copy(
                isCompleted = completedQuests.contains(it.id)
            )
        }
    }

    suspend fun getQuestUIById(questId: Long): Quest? =
        getAllQuestsUI().find { it.id == questId }
}