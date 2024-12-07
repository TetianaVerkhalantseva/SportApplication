package com.example.sportapplication.repository.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.sportapplication.database.data.AchievementType
import com.example.sportapplication.utils.UserStatus

data class AchievementUI (
    val uid: Long,
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val type: AchievementType,
    val isCompleted: Boolean
)

fun isQuestAchievementCompleted(completedQuestsAmount: Int, achievementIndex: Int): Boolean {
    val requiredCompletedQuestsAmount =
        when (achievementIndex) {
            0 -> 1
            1 -> 3
            2 -> 10
            else -> 1
        }
    return completedQuestsAmount >= requiredCompletedQuestsAmount
}

fun isEventAchievementCompleted(completedEventAmount: Int, achievementIndex: Int): Boolean {
    val requiredCompletedEventsAmount =
        when (achievementIndex) {
            0 -> 1
            1 -> 3
            2 -> 10
            else -> 1
        }
    return completedEventAmount >= requiredCompletedEventsAmount
}

fun isRewardExperienceAchievementCompleted(experience: Long, achievementIndex: Int): Boolean {
    val requiredCompletedEventsAmount =
        when (achievementIndex) {
            0 -> 100
            1 -> 3000
            2 -> 5000
            else -> 100
        }
    return experience >= requiredCompletedEventsAmount
}

fun isStatusExperienceAchievementCompleted(experience: Long, achievementIndex: Int): Boolean =
    when (UserStatus.NEW.getUserStatusByExperience(experience)){
        UserStatus.NEW -> achievementIndex == 0
        UserStatus.SKILLED -> achievementIndex < 2
        UserStatus.EXPERT  -> achievementIndex < 2
        UserStatus.PRO -> achievementIndex < 3
    }
