package com.example.sportapplication.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensorData")
data class ItemsData(
    @PrimaryKey(autoGenerate = true) val timestamp: Long,
    @ColumnInfo(name = "item_name") val itemName: String
)