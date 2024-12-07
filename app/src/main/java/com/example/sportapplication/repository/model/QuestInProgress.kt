package com.example.sportapplication.repository.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.sportapplication.database.model.InterestingLocation
import com.example.sportapplication.database.model.Reward
import com.example.sportapplication.database.model.Task

data class QuestInProgress (
    val id: Long,
    @DrawableRes val icon: Int,
    @DrawableRes val image: Int,
    @StringRes val name: Int,
    @StringRes val title: Int,
    @StringRes val description: Int? = null,
    val locationWithTasks: LocationWithTasksInProgress,
    val isCompleted: Boolean,
    val reward : Reward
)


data class LocationWithTasksInProgress (
    val interestingLocation: InterestingLocation,
    val tasks: List<TaskInProgress>
)
data class TaskInProgress(
    val task: Task,
    val isInProgress: Boolean
)