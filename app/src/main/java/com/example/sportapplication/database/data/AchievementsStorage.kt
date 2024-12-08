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
                    icon = R.drawable.icon_1_quest,
                    title = R.string.icon_1_quest,
                    type = AchievementType.QUESTS
                ),
                Achievement(
                    uid = 102,
                    icon = R.drawable.icon_3_quest,
                    title = R.string.icon_3_quest,
                    type = AchievementType.QUESTS
                ),

                Achievement(
                    uid = 103,
                    icon = R.drawable.icon_10_quest,
                    title = R.string.icon_10_quest,
                    type = AchievementType.QUESTS
                ),
            )
        ),
        Pair(
            AchievementType.EVENTS,
            listOf(
                Achievement(
                    uid = 201,
                    icon = R.drawable.icon_1_event,
                    title = R.string.icon_1_event,
                    type = AchievementType.EVENTS
                ),
                Achievement(
                    uid = 202,
                    icon = R.drawable.icon_3_event,
                    title = R.string.icon_3_event,
                    type = AchievementType.EVENTS
                ),

                Achievement(
                    uid = 203,
                    icon = R.drawable.icon_10_event,
                    title = R.string.icon_10_event,
                    type = AchievementType.EVENTS
                ),
            )
        ),
        Pair(
            AchievementType.REWARDS_EXPERIENCE,
            listOf(
                Achievement(
                    uid = 301,
                    icon = R.drawable.icon_100xp,
                    title = R.string.icon_100xp,
                    type = AchievementType.REWARDS_EXPERIENCE
                ),
                Achievement(
                    uid = 302,
                    icon = R.drawable.icon_1000xp,
                    title = R.string.icon_1000xp,
                    type = AchievementType.REWARDS_EXPERIENCE
                ),

                Achievement(
                    uid = 303,
                    icon = R.drawable.icon_3000xp,
                    title = R.string.icon_3000xp,
                    type = AchievementType.REWARDS_EXPERIENCE
                ),
            )
        ),
        Pair(
            AchievementType.STATUS,
            listOf(
                Achievement(
                    uid = 401,
                    icon = R.drawable.icon_new,
                    title = R.string.icon_new,
                    type = AchievementType.STATUS
                ),
                Achievement(
                    uid = 402,
                    icon = R.drawable.icon_skilled,
                    title = R.string.icon_skilled,
                    type = AchievementType.STATUS
                ),

                Achievement(
                    uid = 403,
                    icon = R.drawable.icon_pro,
                    title = R.string.icon_pro,
                    type = AchievementType.STATUS
                ),
            )
        )
    )

}

enum class AchievementType(id: Int) {
    QUESTS(0), EVENTS(1), REWARDS_EXPERIENCE(2), STATUS(3)
}