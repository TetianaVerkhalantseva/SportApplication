package com.example.sportapplication.database.model

import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.mutableLongStateOf

data class ActiveInventoryItem (
    val inventoryId: Long,
    val itemDuration: Long,
    val itemActivated: Long,
    var remainingDuration: MutableLongState

)

fun convertInventoryItemToActiveInventoryItem(inventoryItem: InventoryItem): ActiveInventoryItem{
    val duration = inventoryItem.itemDuration ?: 0L
    val activated = inventoryItem.itemActivated ?: System.currentTimeMillis()
    return ActiveInventoryItem(inventoryId = inventoryItem.inventoryId, itemDuration = duration, itemActivated = activated, remainingDuration = mutableLongStateOf(activated + duration - System.currentTimeMillis()))
}