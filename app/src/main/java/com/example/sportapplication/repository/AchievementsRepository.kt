package com.example.sportapplication.repository

import com.example.sportapplication.database.dao.AchievedEventsDao
import com.example.sportapplication.database.dao.AchievedQuestsDao
import com.example.sportapplication.database.data.AchievementType
import com.example.sportapplication.database.data.AchievementsStorage
import com.example.sportapplication.database.sharedPreferences.AppSharedPreferences
import com.example.sportapplication.mapper.toUi
import com.example.sportapplication.repository.model.AchievementUI
import com.example.sportapplication.repository.model.isEventAchievementCompleted
import com.example.sportapplication.repository.model.isQuestAchievementCompleted
import com.example.sportapplication.repository.model.isRewardExperienceAchievementCompleted
import com.example.sportapplication.repository.model.isStatusExperienceAchievementCompleted
import javax.inject.Inject

class AchievementsRepository @Inject constructor(
    private val achievementsStorage: AchievementsStorage,
    private val achievedQuestsDao: AchievedQuestsDao,
    private val achievedEventsDao: AchievedEventsDao,
    private val prefs: AppSharedPreferences
) {

    suspend fun getUserAchievements(): List<Pair<AchievementType, List<AchievementUI>>> {
        val achievements = achievementsStorage.achievements
        val achievedQuestsAmount = achievedQuestsDao.getAll().size
        val achievedEventsAmount = achievedEventsDao.getAll().size
        val userExperience = prefs.userExperience

        return achievements.map { pair ->
            Pair(
                first = pair.first,
                second = pair.second.mapIndexed { index, achievement ->
                    achievement.toUi(
                        isCompleted =
                        when (pair.first) {
                            AchievementType.QUESTS -> isQuestAchievementCompleted(
                                completedQuestsAmount = achievedQuestsAmount,
                                achievementIndex = index
                            )
                            AchievementType.EVENTS -> isEventAchievementCompleted(
                                completedEventAmount = achievedEventsAmount,
                                achievementIndex = index
                            )
                            AchievementType.REWARDS_EXPERIENCE -> isRewardExperienceAchievementCompleted(
                                experience = userExperience,
                                achievementIndex = index
                            )
                            AchievementType.STATUS -> isStatusExperienceAchievementCompleted(
                                experience = userExperience,
                                achievementIndex = index
                            )
                        }
                    )
                }
            )
        }
    }

    suspend fun getAmountOfAchievedAchievements(): Int {
        var count = 0
        getUserAchievements().forEach {
            it.second.forEach {
                if (it.isCompleted) count++
            }
        }
        return count
    }
}