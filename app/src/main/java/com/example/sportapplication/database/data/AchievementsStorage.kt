package com.example.sportapplication.database.data

import com.example.sportapplication.R
import com.example.sportapplication.database.model.Achievement
import com.example.sportapplication.database.model.Reward
import javax.inject.Inject


class AchievementsStorage @Inject constructor() {

    val achievements = listOf<Achievement>(
        Achievement(
            uid = 1,
            icon = R.drawable.ic_launcher_background,
            title = R.string.achievements,
            notAchievedDescription = R.string.achievements,
            achievedDescription = R.string.achievements,
            type = AchievementType.TOTAL_WORKOUT,
            reward = Reward(
                experience = 1500
            )
        ),
        Achievement(
            uid = 2,
            icon = R.drawable.ic_launcher_foreground,
            title = R.string.quest_screen,
            notAchievedDescription = R.string.achievements,
            achievedDescription = R.string.achievements,
            type = AchievementType.TOTAL_WORKOUT,
            reward = Reward(
                experience = 2500
            )
        ),
        Achievement(
            uid = 101,
            icon = R.drawable.ic_launcher_foreground,
            title = R.string.total_workouts,
            notAchievedDescription = R.string.achievements,
            achievedDescription = R.string.achievements,
            type = AchievementType.WORKOUT_IN_A_WEEK,
            reward = Reward(
                experience = 2500
            )
        ),
        Achievement(
            uid = 102,
            icon = R.drawable.ic_launcher_foreground,
            title = R.string.workouts_in_a_week,
            notAchievedDescription = R.string.achievements,
            achievedDescription = R.string.achievements,
            type = AchievementType.WORKOUT_IN_A_WEEK,
            reward = Reward(
                experience = 2500
            )
        ),
    )

}

enum class AchievementType(id: Int) {
    TOTAL_WORKOUT(0), WORKOUT_IN_A_WEEK(1)
}