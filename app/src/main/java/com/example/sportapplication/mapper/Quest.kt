package com.example.sportapplication.mapper

import com.example.sportapplication.database.model.LocationWithTasks
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.database.model.Task
import com.example.sportapplication.repository.model.LocationWithTasksInProgress
import com.example.sportapplication.repository.model.QuestInProgress
import com.example.sportapplication.repository.model.TaskInProgress

fun Quest.toQuestInProgress(taskInProgressIndex: Int = 0) =
    QuestInProgress(
        id = id,
        icon = icon,
        image = image,
        name = name,
        title = title,
        description = description,
        locationWithTasks = locationWithTasks.toLocationInProgress(taskInProgressIndex),
        isCompleted = isCompleted,
        reward = reward
    )

fun LocationWithTasks.toLocationInProgress(taskInProgressIndex: Int = 0) =
    LocationWithTasksInProgress(
        interestingLocation = interestingLocation,
        tasks = tasks.mapIndexed { index, task ->  task.toTaskInProgress(isInProgress = taskInProgressIndex == index) }

    )

fun Task.toTaskInProgress(isInProgress: Boolean) =
    TaskInProgress(
        task = this,
        isInProgress = isInProgress
    )

fun QuestInProgress.toQuest() =
    Quest(
        id = id,
        icon = icon,
        image = image,
        name = name,
        title = title,
        description = description,
        locationWithTasks = locationWithTasks.toLocation(),
        isCompleted = isCompleted,
        reward = reward
    )

fun LocationWithTasksInProgress.toLocation() =
    LocationWithTasks(
        interestingLocation = interestingLocation,
        tasks = tasks.map { it.toTask() }
    )

fun TaskInProgress.toTask() =
    this.task