package com.example.sportapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("achieved_events")
data class AchievedEvent(
    @PrimaryKey(autoGenerate = false) val id: String
)