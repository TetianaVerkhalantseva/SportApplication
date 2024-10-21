package com.example.sportapplication.database.model

data class Item(
    val itemId: Long,
    val itemName: String,
    var isSelected: Boolean = false
)
