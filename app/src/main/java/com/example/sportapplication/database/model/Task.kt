package com.example.sportapplication.database.model

import androidx.annotation.StringRes

data class Task(
    val id: Long,
    @StringRes val description: Int,
    val isCompleted: Boolean,  // Completion flag for the task
    val requiresPhoto: Boolean, // Indicates if a photo is required for task completion
    val photoUrl: String? = null // URL or path to the photo, if required
)

