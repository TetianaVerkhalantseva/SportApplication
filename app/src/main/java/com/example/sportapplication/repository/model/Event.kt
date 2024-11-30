package com.example.sportapplication.repository.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.sportapplication.database.model.Task

data class Event(
    val id: Long,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    @StringRes val description: Int? = null,
    val locationId: Long,
    val startTime: Long,
    val duration: Long,
    val task: Task? = null,
    val questsIds: List<Long>,
    var isCompleted: Boolean
)