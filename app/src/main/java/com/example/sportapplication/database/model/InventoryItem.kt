package com.example.sportapplication.database.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class InventoryItem(
    val id: Long,
    @DrawableRes val image: Int,
    @StringRes val name: Int,
    var isSelected: Boolean = false,
    val type: InventoryType
)

enum class InventoryType {
    RACKS_AND_BENCHES, RESISTANCE_BANDS
}