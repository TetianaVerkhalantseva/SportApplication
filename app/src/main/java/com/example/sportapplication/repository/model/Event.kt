package com.example.sportapplication.repository.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.sportapplication.database.data.EVENT_REWARD_MULTIPLIER
import com.example.sportapplication.database.model.EventQuest

data class Event(
    val id: Long,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    @StringRes val description: Int? = null,
    val locationId: Long,
    val startTime: Long,
    var duration: Long,
    val questsIds: List<Long>,
    var isCompleted: Boolean
)

data class EventWithQuestsUI(
    val id: Long,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    @StringRes val description: Int? = null,
    val locationId: Long,
    val startTime: Long,
    val duration: Long,
    val quests: List<EventQuest>,
    var isCompleted: Boolean
) {
    fun getTotalReward(): Long {
        var totalReward = 0L
        quests.forEach {
            totalReward += it.reward.experience
        }
        totalReward *= EVENT_REWARD_MULTIPLIER
        return totalReward
    }
}

