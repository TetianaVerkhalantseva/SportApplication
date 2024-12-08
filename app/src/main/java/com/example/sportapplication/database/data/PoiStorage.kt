package com.example.sportapplication.database.data

import com.example.sportapplication.R
import com.example.sportapplication.database.model.EventQuest
import com.example.sportapplication.database.model.EventResponseBody
import com.example.sportapplication.database.model.InterestingLocation
import com.example.sportapplication.database.model.LocationWithTasks
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.database.model.Reward
import com.example.sportapplication.database.model.Task
import javax.inject.Inject
import kotlin.random.Random

const val EVENT_REWARD_MULTIPLIER = 3

class PoiStorage @Inject constructor() {

    // List of events
    // Update if you need the startTime of events on: https://fusionauth.io/dev-tools/date-time
    val eventResponseBodies = listOf(
        EventResponseBody(
            id = 1,
            name = R.string.event_morning_run,
            icon = R.drawable.ic_shoes,
            description = R.string.event_morning_run_description,
            locationId = 7,
            startTime = System.currentTimeMillis(),
            duration = 3600000,
            questsIds = listOf(4, 5),
            rewardItemId = Random.nextInt(1, 4).toLong()
        ),
        EventResponseBody(
            id = 2,
            name = R.string.event_soccer_game,
            icon = R.drawable.ic_fitness_tracker,
            description = R.string.event_soccer_game_description,
            locationId = 3,
            startTime = 1733596261788,
            duration = 5400000,
            questsIds = listOf(1, 2, 3),
            rewardItemId = null)
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
            latitude = 69.682609,
            longitude = 18.965725),
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
            longitude = 18.904906281545866),
        InterestingLocation(
            id = 14,
            name = R.string.location_skansen_festningsverk, //Skansen Festningsverk
            icon = R.drawable.ic_park,
            latitude = 69.653379,
            longitude = 18.964080)
    )

    // List of quests
    val eventQuests = listOf(
        EventQuest(
            id = 1,
            icon = R.drawable.event_guest,
            image = R.drawable.ic_quest_1_image,
            name = R.string.quest_1_name,
            title = R.string.quest_1_title,
            description = R.string.quest_1_description,
            locationWithTasks = listOf(
                LocationWithTasks(
                    interestingLocation = interestingLocations[2],
                    tasks = listOf(
                        Task(id = 1, description = R.string.task_run_two_laps_action, isCompleted = false, requiresPhoto = true),
                        Task(id = 2, description = R.string.task_burpees_30_action, isCompleted = false, requiresPhoto = true),
                        Task(id = 3, description = R.string.task_pushups_50_action, isCompleted = false, requiresPhoto = true),
                    )
                )
            ),
            isCompleted = false,
            reward = Reward(experience = 1000),
            eventId = 2
        ),
        EventQuest(
            id = 2,
            icon = R.drawable.event_guest,
            image = R.drawable.ic_quest_2_image,
            name = R.string.quest_2_name,
            title = R.string.quest_2_title,
            description = R.string.quest_2_description,
            locationWithTasks = listOf(
                LocationWithTasks(
                    interestingLocation = interestingLocations[11],
                    tasks = listOf(
                        Task(id = 2, description = R.string.task_do_squats_action, isCompleted = false, requiresPhoto = true),
                        Task(id = 2, description = R.string.task_burpees_30_action, isCompleted = false, requiresPhoto = true),
                        Task(id = 3, description = R.string.task_pushups_50_action, isCompleted = false, requiresPhoto = true),
                    )
                )
            ),
            isCompleted = false,
            reward = Reward(experience = 800),
            eventId = 2
        ),
        EventQuest(
            id = 3,
            icon = R.drawable.event_guest,
            image = R.drawable.ic_quest_2_image,
            name = R.string.quest_3_name,
            title = R.string.quest_3_title,
            description = R.string.quest_3_description,
            locationWithTasks = listOf(
                LocationWithTasks(
                    interestingLocation = interestingLocations[12],
                    tasks = listOf(
                        Task(id = 2, description = R.string.task_bike_10k_photo, isCompleted = false, requiresPhoto = true),
                        Task(id = 2, description = R.string.task_bike_action, isCompleted = false, requiresPhoto = true)
                    )
                )
            ),
            isCompleted = false,
            reward = Reward(experience = 800),
            eventId = 2
        ),
        EventQuest(
            id = 4,
            icon = R.drawable.event_guest,
            image = R.drawable.ic_quest_2_image,
            name = R.string.quest_3_name,
            title = R.string.quest_3_title,
            description = R.string.quest_3_description,
            locationWithTasks = listOf(
                LocationWithTasks(
                    interestingLocation = interestingLocations[6],
                    tasks = listOf(
                        Task(id = 2, description = R.string.task_bike_10k_photo, isCompleted = false, requiresPhoto = true),
                        Task(id = 2, description = R.string.task_bike_action, isCompleted = false, requiresPhoto = true)
                    )
                )
            ),
            isCompleted = false,
            reward = Reward(experience = 800),
            eventId = 1
        ),
        EventQuest(
            id = 5,
            icon = R.drawable.event_guest,
            image = R.drawable.ic_quest_2_image,
            name = R.string.quest_3_name,
            title = R.string.quest_3_title,
            description = R.string.quest_3_description,
            locationWithTasks = listOf(
                LocationWithTasks(
                    interestingLocation = interestingLocations[0],
                    tasks = listOf(
                        Task(id = 2, description = R.string.task_bike_10k_photo, isCompleted = false, requiresPhoto = true),
                        Task(id = 2, description = R.string.task_bike_action, isCompleted = false, requiresPhoto = true)
                    )
                )
            ),
            isCompleted = false,
            reward = Reward(experience = 800),
            eventId = 1
        )

    )

    val quests = listOf(
        Quest(
            id = 1,
            icon = R.drawable.quest_icon,
            image = R.drawable.ic_quest_2_image,
            name = R.string.quest_1_name,
            title = R.string.quest_3_title,
            description = R.string.quest_3_description,
            locationWithTasks =
            LocationWithTasks(
                interestingLocation = interestingLocations[8],
                tasks = listOf(
                    Task(id = 2, description = R.string.task_bike_10k_photo, isCompleted = false, requiresPhoto = true),
                    Task(id = 2, description = R.string.task_bike_action, isCompleted = false, requiresPhoto = true)
                )

            ),
            isCompleted = false,
            reward = Reward(experience = 800)
        ),
        Quest(
            id = 2,
            icon = R.drawable.quest_icon,
            image = R.drawable.ic_quest_2_image,
            name = R.string.quest_2_name,
            title = R.string.quest_3_title,
            description = R.string.quest_3_description,
            locationWithTasks =
            LocationWithTasks(
                interestingLocation = interestingLocations[7],
                tasks = listOf(
                    Task(id = 2, description = R.string.task_bike_10k_photo, isCompleted = false, requiresPhoto = true),
                    Task(id = 2, description = R.string.task_bike_action, isCompleted = false, requiresPhoto = true)
                )
            ),
            isCompleted = false,
            reward = Reward(experience = 800)
        ),
    )
}


