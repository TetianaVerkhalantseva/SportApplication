package com.example.sportapplication.database.data

import com.example.sportapplication.R
import com.example.sportapplication.database.model.Event
import com.example.sportapplication.database.model.InterestingLocation
import com.example.sportapplication.database.model.Task
import com.example.sportapplication.database.model.LocationWithTasks
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.database.model.Reward
import javax.inject.Inject

class PoiStorage @Inject constructor() {

    // List of events
    val events = listOf(
        Event(id = 1, name = R.string.event_morning_run, icon = R.drawable.ic_shoes, description = R.string.event_morning_run_description, locationId = 13, startTime = 1627804800000, duration = 3600000, reward = "Shoes"),
        Event(id = 2, name = R.string.event_soccer_game, icon = R.drawable.ic_fitness_tracker, description = R.string.event_soccer_game_description, locationId = 3, startTime = 1627891200000, duration = 5400000, reward = "Ball")
    )

    // List of interestingLocations
    val interestingLocations = listOf(
        InterestingLocation(id = 1, name = R.string.location_city_park, icon = R.drawable.ic_park, latitude = 69.65843464971493, longitude = 18.935566551286925),  // Prestvannet Lake
        InterestingLocation(id = 2, name = R.string.location_stadium, icon = R.drawable.ic_football, latitude = 69.6759505811548, longitude = 18.956892332225188),  // Tromsøhallen
        InterestingLocation(id = 3, name = R.string.location_romssa_arena, icon = R.drawable.ic_stadium, latitude = 69.64900982938423, longitude = 18.9343836848801),  // Romssa Arena
        InterestingLocation(id = 4, name = R.string.location_tromsobadet, icon = R.drawable.ic_swimming, latitude = 69.67446992211698, longitude = 18.954854769538684),  // Tromsøbadet
        InterestingLocation(id = 5, name = R.string.location_tromso_alpinpark, icon = R.drawable.ic_skiing, latitude = 69.67675135993744, longitude = 19.069468513717972), // Tromsø Alpinpark
        InterestingLocation(id = 6, name = R.string.location_tuil_arena, icon = R.drawable.ic_stadium, latitude = 69.64563605572478, longitude = 19.014560525357513),  // TUIL Arena
        InterestingLocation(id = 7, name = R.string.location_kraft_sportssenter, icon = R.drawable.ic_gym, latitude = 69.68284244169863, longitude = 18.965686224149145),  // Kraft sportssenter
        InterestingLocation(id = 8, name = R.string.location_fløyahallen, icon = R.drawable.ic_volleyball, latitude = 69.67796808127727, longitude = 18.959066269538987),  // Fløyahallen
        InterestingLocation(id = 9, name = R.string.location_skarpmarka, icon = R.drawable.ic_football, latitude = 69.68676552933687, longitude = 18.93784403629443),  // Skarpmarka
        InterestingLocation(id = 10, name = R.string.location_kroken_car_park, icon = R.drawable.ic_park, latitude = 69.67863794811923, longitude = 19.07110784468436),  // Kroken car park
        InterestingLocation(id = 11, name = R.string.location_valhallhallen, icon = R.drawable.ic_handball, latitude = 69.66028463683959, longitude = 18.955950443605804),  // Valhallhallen
        InterestingLocation(id = 12, name = R.string.location_crossfit_nordaforr, icon = R.drawable.ic_gym, latitude = 69.64078847076787, longitude = 18.93634199924607),  // CrossFit Nordaførr
        InterestingLocation(id = 13, name = R.string.location_folkeparken, icon = R.drawable.ic_park, latitude = 69.63556417110154, longitude = 18.904906281545866)  // Folkeparken
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


