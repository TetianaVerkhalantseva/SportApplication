package com.example.sportapplication.mapper

import com.example.sportapplication.database.model.EventResponseBody
import com.example.sportapplication.repository.model.Event


fun EventResponseBody.toEvent(isCompleted: Boolean = false) =
    Event(
        id = id,
        name = name,
        icon = icon,
        description = description,
        locationId = locationId,
        startTime = startTime,
        duration = duration,
        task = task,
        questsIds = questsIds,
        isCompleted = isCompleted
    )

fun Event.toResponseBody() =
    EventResponseBody(
        id = id,
        name = name,
        icon = icon,
        description = description,
        locationId = locationId,
        startTime = startTime,
        duration = duration,
        task = task,
        questsIds = questsIds
    )
