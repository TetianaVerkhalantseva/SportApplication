package com.example.sportapplication.database.model

data class LocationWithTasks(
    val interestingLocation: InterestingLocation,
    val tasks: List<Task>   // List of tasks associated with this interestingLocation
)

