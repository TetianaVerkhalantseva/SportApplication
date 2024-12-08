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
    @ColumnInfo(name="item_category") val itemCategory: ItemCategory,
    @ColumnInfo(name="item_activated") var itemActivated: Long?,
    @ColumnInfo(name="item_duration") val itemDuration: Long?,
    @ColumnInfo(name="item_effect_on_xp") val itemEffectOnXp: Float?,
    @ColumnInfo(name="item_effect_on_duration") val itemEffectOnDuration: Float?
)