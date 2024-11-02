package com.example.sportapplication.repository

import com.example.sportapplication.database.data.PoiStorage
import com.example.sportapplication.database.model.Event
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.database.model.Reward
import com.example.sportapplication.database.model.Task
import javax.inject.Inject

class PoiRepository @Inject constructor(
    private val poiStorage: PoiStorage
) {
    fun getQuests() = poiStorage.quests

    fun getEvents() = poiStorage.events

    fun getLocations() = poiStorage.interestingLocations

    fun findQuestById(id: Long) = poiStorage.quests.find { it.id == id }

    fun findEventById(id: Long) = poiStorage.events.find { it.id == id }

    fun findLocationById(id: Long) = poiStorage.interestingLocations.find { it.id == id }

    fun isEventActive(event: Event, currentTime: Long): Boolean {
        return event.isActive(currentTime)
    }

    // Checks if a task is completed
    fun isTaskCompleted(task: Task?): Boolean {
        // If there is no task, it is considered completed
        if (task == null) return true

        // If the task is completed and a photo is required, check if the photo is uploaded
        return task.isCompleted && (!task.requiresPhoto || task.photoUrl != null)
    }

    // Full check to determine if the event is completed
    fun isEventCompleted(event: Event, userAtLocation: Boolean, currentTime: Long): Boolean {
        // Check if the event is active and if the user is at the interestingLocation
        if (!isEventActive(event, currentTime) || !userAtLocation) return false

        // Check if the task is completed (if a task exists)
        return isTaskCompleted(event.task)
    }

    // Find quests by interestingLocation ID
    fun findQuestsByLocation(locationId: Long) = poiStorage.quests.filter { quest ->
        quest.locationWithTasks.any { it.interestingLocation.id == locationId }
    }

    fun findEventsByLocation(locationId: Long) = poiStorage.events.filter { it.locationId == locationId }

    // Find all tasks for a specific interestingLocation
    fun getTasksForLocation(locationId: Long): List<Task> {
        return poiStorage.quests.flatMap { quest ->
            quest.locationWithTasks.filter { it.interestingLocation.id == locationId }.flatMap { it.tasks }
        }
    }

    // Find if a specific task is completed at a interestingLocation
    fun isTaskCompletedAtLocation(locationId: Long, taskId: Long): Boolean {
        val tasks = getTasksForLocation(locationId)
        val task = tasks.find { it.id == taskId }
        return isTaskCompleted(task)
    }

    // Check if all tasks are completed at a interestingLocation
    fun areAllTasksCompletedAtLocation(locationId: Long): Boolean {
        val tasks = getTasksForLocation(locationId)
        return tasks.all { isTaskCompleted(it) }
    }

    // Check if the quest is completed
    fun isQuestCompleted(quest: Quest): Boolean {
        return quest.checkCompletion()
    }

    // Get the reward for completing a quest
    fun getRewardForQuest(questId: Long): Reward? {
        val quest = findQuestById(questId)
        return if (isQuestCompleted(quest!!)) quest.reward else null
    }
}
