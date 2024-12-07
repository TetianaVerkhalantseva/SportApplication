package com.example.sportapplication.mapper

import com.example.sportapplication.database.model.EventQuest
import com.example.sportapplication.database.model.EventResponseBody
import com.example.sportapplication.repository.model.Event
import com.example.sportapplication.repository.model.EventWithQuestsUI


fun EventResponseBody.toEvent(isCompleted: Boolean = false) =
    Event(
        id = id,
        name = name,
        icon = icon,
        description = description,
        locationId = locationId,
        startTime = startTime,
        duration = duration,
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
        questsIds = questsIds
    )

fun EventResponseBody.toEventWithQuestsUI(quests: List<EventQuest>, isCompleted: Boolean) =
    EventWithQuestsUI(
        id = id,
        name = name,
        icon = icon,
        description = description,
        locationId = locationId,
        startTime = startTime,
        duration = duration,
        quests = quests,
        isCompleted = isCompleted
    )
