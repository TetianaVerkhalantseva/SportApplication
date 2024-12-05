package com.example.sportapplication.database.model

import androidx.annotation.DrawableRes

data class InventoryItem(
    val inventoryId: Long,
    val itemId: Long,
    @DrawableRes val image: Int?,
    val itemName: String,
    var isSelected: Boolean = false
)


