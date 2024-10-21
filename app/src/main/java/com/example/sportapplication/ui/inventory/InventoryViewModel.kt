package com.example.sportapplication.ui.inventory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sportapplication.database.dao.InventoryDao
import com.example.sportapplication.database.dao.ItemsDao
import com.example.sportapplication.database.entity.InventoryData
import com.example.sportapplication.database.entity.ItemsData
import com.example.sportapplication.database.model.InventoryItem
import com.example.sportapplication.database.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val inventoryDao: InventoryDao,
    private val itemsDao: ItemsDao
) : ViewModel() {
    var inventoryItems by mutableStateOf(
        arrayListOf(
            InventoryItem(
                inventoryId = null,
                itemId = -1,
                itemName = "No Items",
                image = null
            )
        )
    )
    var items by mutableStateOf(
        arrayListOf(
            Item(
                itemId = -1,
                itemName = "No Items"
            )
        )
    )

    init {
        prepopulateItems()
        getAllItems()
        getAllInventoryItems()
    }

    private fun getAllItems() {
        CoroutineScope(Dispatchers.IO).launch {
            val tempItems: ArrayList<Item> = ArrayList()

            val itemsFromDao = itemsDao.getAll()

            itemsFromDao.forEach { item ->
                tempItems.add(
                    Item(
                        itemName = item.itemName,
                        itemId = item.itemId
                    )
                )
            }

            items = tempItems

        }
    }

    fun addToInventory(item: Item) {
        CoroutineScope(Dispatchers.IO).launch {
            inventoryDao.insertItem(
                InventoryData(
                    inventoryItemId = 0,
                    itemId = item.itemId,
                    itemName = item.itemName
                )
            )

            val inventoryFromDao = inventoryDao.getAll()
            val tempInventory: ArrayList<InventoryItem> = ArrayList()
            inventoryFromDao.forEach { item ->
                tempInventory.add(
                    InventoryItem(
                        inventoryId = item.inventoryItemId,
                        itemName = item.itemName,
                        itemId = item.itemId,
                        image = null
                    )
                )
            }

            inventoryItems = tempInventory
        }
    }


    private fun getAllInventoryItems() {
        CoroutineScope(Dispatchers.IO).launch {
            val tempInventoryItems: ArrayList<InventoryItem> = ArrayList()
            val inventory = inventoryDao.getAll()

            if (inventory.isNotEmpty()) {
                inventory.forEach { item ->
                    tempInventoryItems.add(
                        InventoryItem(
                            inventoryId = item.inventoryItemId,
                            itemName = item.itemName,
                            itemId = item.itemId,
                            image = null
                        )
                    )
                }

                inventoryItems = tempInventoryItems
            }

        }
    }

    private fun prepopulateItems() {
        CoroutineScope(Dispatchers.IO).launch {
            val itemsInDao = itemsDao.getAll()
            if (itemsInDao.size < 1) {
                val prepopItems = arrayOf(
                    Item(itemId = 0, itemName = "TemporaryItem1"),
                    Item(itemId = 0, itemName = "TemporaryItem2"),
                    Item(itemId = 0, itemName = "TemporaryItem3"),
                )
                prepopItems.forEach { item ->
                    itemsDao.insertItem(
                        ItemsData(
                            itemId = item.itemId,
                            itemName = item.itemName
                        )
                    )
                }

            }
        }
    }

}