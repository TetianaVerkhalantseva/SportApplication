package com.example.sportapplication.database.data

import com.example.sportapplication.R
import com.example.sportapplication.database.model.Achievement
import javax.inject.Inject


class AchievementsStorage @Inject constructor() {

    val achievements = listOf<Pair<AchievementType, List<Achievement>>>(
        Pair(
            AchievementType.QUESTS,
            listOf(
                Achievement(
                    uid = 101,
                    icon = R.drawable.pro,
                    title = R.string.achievements,
                    type = AchievementType.QUESTS
                ),
                Achievement(
                    uid = 102,
                    icon = R.drawable.pro,
                    title = R.string.quest_screen,
                    type = AchievementType.QUESTS
                ),

                Achievement(
                    uid = 103,
                    icon = R.drawable.pro,
                    title = R.string.total_workouts,
                    type = AchievementType.QUESTS
                ),
            )
        ),
        Pair(
            AchievementType.EVENTS,
            listOf(
                Achievement(
                    uid = 201,
                    icon = R.drawable.pro,
                    title = R.string.achievements,
                    type = AchievementType.EVENTS
                ),
                Achievement(
                    uid = 202,
                    icon = R.drawable.pro,
                    title = R.string.quest_screen,
                    type = AchievementType.EVENTS
                ),

                Achievement(
                    uid = 203,
                    icon = R.drawable.pro,
                    title = R.string.total_workouts,
                    type = AchievementType.EVENTS
                ),
            )
        ),
        Pair(
            AchievementType.REWARDS_EXPERIENCE,
            listOf(
                Achievement(
                    uid = 301,
                    icon = R.drawable.pro,
                    title = R.string.achievements,
                    type = AchievementType.REWARDS_EXPERIENCE
                ),
                Achievement(
                    uid = 302,
                    icon = R.drawable.pro,
                    title = R.string.quest_screen,
                    type = AchievementType.REWARDS_EXPERIENCE
                ),

                Achievement(
                    uid = 303,
                    icon = R.drawable.pro,
                    title = R.string.total_workouts,
                    type = AchievementType.REWARDS_EXPERIENCE
                ),
            )
        ),
        Pair(
            AchievementType.STATUS,
            listOf(
                Achievement(
                    uid = 401,
                    icon = R.drawable.pro,
                    title = R.string.achievements,
                    type = AchievementType.STATUS
                ),
                Achievement(
                    uid = 402,
                    icon = R.drawable.pro,
                    title = R.string.quest_screen,
                    type = AchievementType.STATUS
                ),

                Achievement(
                    uid = 403,
                    icon = R.drawable.pro,
                    title = R.string.total_workouts,
                    type = AchievementType.STATUS
                ),
            )
        )
    )

}

enum class AchievementType(id: Int) {
    QUESTS(0), EVENTS(1), REWARDS_EXPERIENCE(2), STATUS(3)
}