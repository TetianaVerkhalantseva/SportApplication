package com.example.sportapplication.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itemsData")
data class ItemsData(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "item_id") val itemId: Long,
    @ColumnInfo(name = "item_name") val itemName: String,
    @ColumnInfo(name="item_type") val itemType: ItemType,
    @ColumnInfo(name="item_category") val itemCategory: ItemCategory
)

enum class ItemType {
    INAPPLICABLE, ACTIVE, PASSIVE
}

enum class ItemCategory {
        PLACEHOLDER, DRINK, CHOCOLATEBAR, SNEAKER
}