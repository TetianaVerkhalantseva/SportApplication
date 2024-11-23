package com.example.sportapplication.database.data

import com.example.sportapplication.R
import com.example.sportapplication.database.model.Event
import com.example.sportapplication.database.model.InterestingLocation
import com.example.sportapplication.database.model.LocationWithTasks
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.database.model.Reward
import com.example.sportapplication.database.model.Task
import javax.inject.Inject

class PoiStorage @Inject constructor() {

    // List of events
    val events = listOf(
        Event(
            id = 1,
            name = R.string.event_morning_run,
            icon = R.drawable.ic_shoes,
            description = R.string.event_morning_run_description,
            locationId = 13,
            startTime = 1732324252665,
            duration = 3600000,
            questsIds = listOf(1, 2)),
        Event(
            id = 2,
            name = R.string.event_soccer_game,
            icon = R.drawable.ic_fitness_tracker,
            description = R.string.event_soccer_game_description,
            locationId = 3,
            startTime = 1732324252665,
            duration = 5400000,
            questsIds = listOf(2)),
        Event(
            id = 3,
            name = R.string.event_soccer_game,
            icon = R.drawable.ic_fitness_tracker,
            description = R.string.event_soccer_game_description,
            locationId = 6,
            startTime = 1732324252665,
            duration = 1800000,
            questsIds = listOf(2)),
        Event(
            id = 4,
            name = R.string.event_soccer_game,
            icon = R.drawable.ic_fitness_tracker,
            description = R.string.event_soccer_game_description,
            locationId = 8,
            startTime = 1732324252665,
            duration = 2700000,
            questsIds = listOf(2)),
        Event(
            id = 5,
            name = R.string.event_soccer_game,
            icon = R.drawable.ic_fitness_tracker,
            description = R.string.event_soccer_game_description,
            locationId = 1,
            startTime = 1732324252665,
            duration = 7200000,
            questsIds = listOf(2)),
        Event(
            id = 6,
            name = R.string.event_soccer_game,
            icon = R.drawable.ic_fitness_tracker,
            description = R.string.event_soccer_game_description,
            locationId = 10,
            startTime = 1732324252665,
            duration = 10800000,
            questsIds = listOf(2))
    )

    // List of interestingLocations
    val interestingLocations = listOf(
        InterestingLocation(
            id = 1, name = R.string.location_city_park, // Prestvannet Lake
            icon = R.drawable.ic_park,
            latitude = 69.65843464971493,
            longitude = 18.935566551286925),
        InterestingLocation(
            id = 2,
            name = R.string.location_stadium,
            icon = R.drawable.ic_football,
            latitude = 69.6759505811548,
            longitude = 18.956892332225188),  // Tromsøhallen
        InterestingLocation(
            id = 3,
            name = R.string.location_romssa_arena, // Romssa Arena
            icon = R.drawable.ic_stadium,
            latitude = 69.64900982938423,
            longitude = 18.9343836848801),
        InterestingLocation(
            id = 4,
            name = R.string.location_tromsobadet, // Tromsøbadet
            icon = R.drawable.ic_swimming,
            latitude = 69.67446992211698,
            longitude = 18.954854769538684),
        InterestingLocation(
            id = 5,
            name = R.string.location_tromso_alpinpark, // Tromsø Alpinpark
            icon = R.drawable.ic_skiing,
            latitude = 69.67675135993744,
            longitude = 19.069468513717972),
        InterestingLocation(
            id = 6,
            name = R.string.location_tuil_arena, // TUIL Arena
            icon = R.drawable.ic_stadium,
            latitude = 69.64563605572478,
            longitude = 19.014560525357513),
        InterestingLocation(
            id = 7,
            name = R.string.location_kraft_sportssenter, // Kraft sportssenter
            icon = R.drawable.ic_gym,
            latitude = 69.68284244169863,
            longitude = 18.965686224149145),
        InterestingLocation(
            id = 8,
            name = R.string.location_fløyahallen, // Fløyahallen
            icon = R.drawable.ic_volleyball,
            latitude = 69.67796808127727,
            longitude = 18.959066269538987),
        InterestingLocation(
            id = 9,
            name = R.string.location_skarpmarka, // Skarpmarka
            icon = R.drawable.ic_football,
            latitude = 69.68676552933687,
            longitude = 18.93784403629443),
        InterestingLocation(
            id = 10,
            name = R.string.location_kroken_car_park, // Kroken car park
            icon = R.drawable.ic_park,
            latitude = 69.67863794811923,
            longitude = 19.07110784468436),
        InterestingLocation(
            id = 11,
            name = R.string.location_valhallhallen, // Valhallhallen
            icon = R.drawable.ic_handball,
            latitude = 69.66028463683959,
            longitude = 18.955950443605804),
        InterestingLocation(
            id = 12,
            name = R.string.location_crossfit_nordaforr, // CrossFit Nordaførr
            icon = R.drawable.ic_gym,
            latitude = 69.64078847076787,
            longitude = 18.93634199924607),
        InterestingLocation(
            id = 13,
            name = R.string.location_folkeparken, // Folkeparken
            icon = R.drawable.ic_park,
            latitude = 69.63556417110154,
            longitude = 18.904906281545866)
    )

    // List of quests
    val quests = listOf(
        Quest(
            id = 1,
            icon = R.drawable.ic_quest_1,
            image = R.drawable.ic_quest_1_image,
            name = R.string.quest_1_name,
            title = R.string.quest_1_title,
            description = R.string.quest_1_description,
            locationWithTasks = listOf(
                LocationWithTasks(
                    interestingLocation = interestingLocations[0],
                    tasks = listOf(
                        Task(id = 1, description = R.string.task_run_two_laps_action, isCompleted = false, requiresPhoto = true)
                    )
                )
            ),
            isCompleted = false,
            reward = Reward(experience = 1000)
        ),
        Quest(
            id = 2,
            icon = R.drawable.ic_quest_2,
            image = R.drawable.ic_quest_2_image,
            name = R.string.quest_2_name,
            title = R.string.quest_2_title,
            description = R.string.quest_2_description,
            locationWithTasks = listOf(
                LocationWithTasks(
                    interestingLocation = interestingLocations[11],
                    tasks = listOf(
                        Task(id = 2, description = R.string.task_do_squats_action, isCompleted = false, requiresPhoto = true)
                    )
                )
            ),
            isCompleted = false,
            reward = Reward(experience = 800)
        )
    )
}

