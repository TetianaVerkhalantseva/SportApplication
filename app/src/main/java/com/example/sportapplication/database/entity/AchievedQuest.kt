package com.example.sportapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("achieved_quests")
data class AchievedQuest(
    @PrimaryKey(autoGenerate = false) val id: Long
)