package com.example.sportapplication.database.model

import com.example.sportapplication.database.entity.ItemCategory
import com.example.sportapplication.database.entity.ItemType

data class Item(
    val itemId: Long,
    val itemName: String,
    val itemType: ItemType,
    val itemCategory: ItemCategory
)
