package com.example.sportapplication.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventoryData")
data class InventoryData(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name="inventory_id") val inventoryId: Long,
    @ColumnInfo(name = "item_id") val itemId: Long,
    @ColumnInfo(name = "item_name") val itemName: String,
    @ColumnInfo(name = "item_type") val itemType: ItemType,
    @ColumnInfo(name="item_category") val itemCategory: ItemCategory
)