package com.example.sportapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val avatarId: Int = 0 // Default avatar
)