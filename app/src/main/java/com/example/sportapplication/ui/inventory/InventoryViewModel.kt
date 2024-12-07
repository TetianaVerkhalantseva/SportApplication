package com.example.sportapplication.ui.inventory

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.database.data.ItemRepository
import com.example.sportapplication.database.entity.ItemCategory
import com.example.sportapplication.database.entity.ItemType
import com.example.sportapplication.database.model.InventoryItem
import com.example.sportapplication.database.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    private val emptyInventoryItem = InventoryItem(
        inventoryId = -1,
        itemId = -1,
        itemName = "No Items",
        itemCategory = ItemCategory.PLACEHOLDER,
        itemType = ItemType.INAPPLICABLE
    )

    private val emptyItemsItem = Item(
        itemId = -1,
        itemName = "No Items",
        itemType = ItemType.INAPPLICABLE,
        itemCategory = ItemCategory.PLACEHOLDER
    )

    var inventoryItems =
        mutableStateListOf(
            emptyInventoryItem
        )

    var items =
        mutableStateListOf(
            emptyItemsItem
        )

    init {
        viewModelScope.launch(){
            itemRepository.prepopulateItems()

            items.clear()
            itemRepository.getAllItems().forEach{
                item -> items.add(Item(itemId = item.itemId, itemName = item.itemName, itemType = item.itemType, itemCategory = item.itemCategory))
            }


            val inventoryInDao = itemRepository.getAllInventory()
            if(inventoryInDao.isNotEmpty()){
                inventoryItems.clear()
                inventoryInDao.forEach{ item ->
                    inventoryItems.add(InventoryItem(inventoryId = item.inventoryId, itemId = item.itemId, itemName = item.itemName, itemType = item.itemType, itemCategory = item.itemCategory  ))
                }
            }

        }
    }

    fun addItemToInventoryById(itemId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val item = itemRepository.getItemById(itemId)

            val updatedInventory = itemRepository.insertItemToInventory(item)

            if(updatedInventory.isNotEmpty()){
                inventoryItems.clear()
                updatedInventory.forEach { inventoryItem -> inventoryItems.add(inventoryItem) }
            }


        }
    }

    fun removeItemFromInventoryById(inventoryId: Long){
        CoroutineScope(Dispatchers.IO).launch {
            val updatedInventory = itemRepository.removeItemFromInventory(inventoryId)
            inventoryItems.clear()

            if(updatedInventory.isEmpty()){
                inventoryItems.add(emptyInventoryItem)
            } else{
                updatedInventory.forEach{inventoryItem -> inventoryItems.add(inventoryItem)}
            }

        }
    }

}