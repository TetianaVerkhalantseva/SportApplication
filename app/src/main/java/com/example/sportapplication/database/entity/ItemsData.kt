package com.example.sportapplication.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itemsData")
data class ItemsData(
    @PrimaryKey(autoGenerate = true) val itemId: Long,
    @ColumnInfo(name = "item_name") val itemName: String,
)