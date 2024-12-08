package com.example.sportapplication.database.model

import com.example.sportapplication.R
import com.example.sportapplication.database.entity.ItemCategory
import com.example.sportapplication.database.entity.ItemType

data class InventoryItem(
    val inventoryId: Long,
    val itemId: Long,
    val itemName: String,
    val itemType: ItemType,
    val itemCategory: ItemCategory,
    val itemActivated: Long?,
    val itemDuration: Long?,
    val itemEffectOnXp: Float?,
    val itemEffectOnDuration: Float?
)


fun itemCategoryToDrawable(itemCategory: ItemCategory): Int {
    if(itemCategory == ItemCategory.SNEAKER){
        return R.drawable.item_icon_sneaker
    }
    if(itemCategory == ItemCategory.DRINK){
        return R.drawable.item_icon_energy_drink
    }
    if(itemCategory == ItemCategory.CHOCOLATEBAR){
        return R.drawable.item_icon_energy_bar
    }

    return R.drawable.ic_launcher_background
}