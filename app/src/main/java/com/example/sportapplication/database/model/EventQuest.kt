package com.example.sportapplication.database.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class EventQuest (
    val id: Long,
    @DrawableRes val icon: Int,
    @DrawableRes val image: Int,
    @StringRes val name: Int,
    @StringRes val title: Int,
    @StringRes val description: Int? = null,
    val locationWithTasks: List<LocationWithTasks>,
    val isCompleted: Boolean,
    val reward : Reward,
    val eventId: Long
) {
    // Function to check if all tasks in the quest are completed
    fun checkCompletion(): Boolean {
        return locationWithTasks.all { locationTasks ->
            locationTasks.tasks.all { it.isCompleted && (!it.requiresPhoto || it.photoUrl != null) }
        }
    }
}

data class Quest (
    val id: Long,
    @DrawableRes val icon: Int,
    @DrawableRes val image: Int,
    @StringRes val name: Int,
    @StringRes val title: Int,
    @StringRes val description: Int? = null,
    val locationWithTasks: LocationWithTasks,
    val isCompleted: Boolean,
    val reward : Reward
)