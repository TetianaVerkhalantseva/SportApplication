package com.example.sportapplication.repository

import com.example.sportapplication.database.data.AchievementType
import com.example.sportapplication.database.data.AchievementsStorage
import com.example.sportapplication.database.model.Achievement
import javax.inject.Inject

class AchievementsRepository @Inject constructor(
    private val achievementsStorage: AchievementsStorage
) {

    private var achievements : Map<AchievementType, MutableList<Achievement>>? = null

    suspend fun getUserAchievements(): Map<AchievementType, MutableList<Achievement>> {
        return if (achievements == null) {
            val achievements = achievementsStorage.achievements
            val pairedAchievements = mutableMapOf<AchievementType, MutableList<Achievement>>()
            AchievementType.entries.forEach {
                pairedAchievements[it] = mutableListOf()
            }
            achievements.forEach {
                pairedAchievements[it.type]?.add(it)
            }
            this.achievements =  pairedAchievements
            pairedAchievements
        } else achievements!!
    }

    suspend fun getAchievementById(id: Long): Achievement? {
        val currentAchievements = achievements ?: getUserAchievements()
        currentAchievements.forEach {  pair ->
            pair.value.forEach { achievement ->
                if (achievement.uid == id) return achievement
            }
        }
        return null
    }

}