package com.example.sportapplication.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensorData")
data class InventoryData(
    @PrimaryKey(autoGenerate = true) val inventoryItem: Long,
    @ColumnInfo(name = "item_id") val itemId: Long,
    @ColumnInfo(name="item_name")val itemName: String
)