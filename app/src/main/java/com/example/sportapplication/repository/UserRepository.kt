package com.example.sportapplication.repository

import androidx.lifecycle.LiveData
import com.example.sportapplication.database.dao.AchievedEventsDao
import com.example.sportapplication.database.dao.UserDao
import com.example.sportapplication.database.entity.AchievedEvent
import com.example.sportapplication.database.entity.User
import com.example.sportapplication.database.model.Event
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val achievedEventsDao: AchievedEventsDao,
    private val poiRepository: PoiRepository,
){

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    suspend fun insertAchievedEvent(eventId: String) {
        achievedEventsDao.insert(AchievedEvent(id = eventId))
    }

    fun getAllAchievedEventsLiveData(): LiveData<List<AchievedEvent>> =
        achievedEventsDao.getAllLiveData()

    suspend fun getAllAchievedEvents(): List<AchievedEvent> =
        achievedEventsDao.getAll()

    suspend fun getAllNotAchievedEvents(): List<Event> {
        val events = poiRepository.getEvents()
        val achievedEvents = achievedEventsDao.getAll()
        return events.filter { event ->
            achievedEvents.find { it.id == event.id.toString() } == null
        }
    }
}