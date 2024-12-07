package com.example.sportapplication.repository

import com.example.sportapplication.database.data.PoiStorage
import com.example.sportapplication.database.model.EventResponseBody
import com.example.sportapplication.database.model.Task
import javax.inject.Inject

class PoiRepository @Inject constructor(
    private val poiStorage: PoiStorage
) {
    fun getEventsQuests() = poiStorage.eventQuests

    fun getQuests() = poiStorage.quests

    fun getEvents() = poiStorage.eventResponseBodies

    fun getLocations() = poiStorage.interestingLocations

    fun findQuestById(id: Long) = poiStorage.eventQuests.find { it.id == id }

    fun findEventById(id: Long) = poiStorage.eventResponseBodies.find { it.id == id }

    fun findLocationById(id: Long) = poiStorage.interestingLocations.find { it.id == id }

    fun isEventActive(eventResponseBody: EventResponseBody, currentTime: Long): Boolean {
        return eventResponseBody.isActive(currentTime)
    }

    // Checks if a task is completed
    fun isTaskCompleted(task: Task?): Boolean {
        // If there is no task, it is considered completed
        if (task == null) return true

        // If the task is completed and a photo is required, check if the photo is uploaded
        return task.isCompleted && (!task.requiresPhoto || task.photoUrl != null)
    }

    // Full check to determine if the event is completed
    fun isEventCompleted(eventResponseBody: EventResponseBody, userAtLocation: Boolean, currentTime: Long): Boolean {
        // Check if the event is active and if the user is at the interestingLocation
        if (!isEventActive(eventResponseBody, currentTime) || !userAtLocation) return false

        // Check if the task is completed (if a task exists)
        return isTaskCompleted(eventResponseBody.task)
    }

    // Find quests by interestingLocation ID
    fun findQuestsByLocation(locationId: Long) = poiStorage.eventQuests.filter { quest ->
        quest.locationWithTasks.any { it.interestingLocation.id == locationId }
    }

    fun findEventsByLocation(locationId: Long) = poiStorage.eventResponseBodies.filter { it.locationId == locationId }

    // Find all tasks for a specific interestingLocation
    fun getTasksForLocation(locationId: Long): List<Task> {
        return poiStorage.eventQuests.flatMap { quest ->
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
}
