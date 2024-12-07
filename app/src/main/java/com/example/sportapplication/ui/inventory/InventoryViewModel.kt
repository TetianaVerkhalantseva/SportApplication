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

    var inventoryItems =
        mutableStateListOf(
            emptyInventoryItem
        )

    var items =
        mutableStateListOf(
            Item(
                itemId = -1,
                itemName = "No Items",
                itemType = ItemType.INAPPLICABLE,
                itemCategory = ItemCategory.PLACEHOLDER
            )
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



    //val allItems = itemRepository.getAllItems()



/*

    init {
        prepopulateItems()
        getAllItems()
        getAllInventoryItems()
        Log.i("Items Population", items.size.toString())
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

            if (itemsInDao.isEmpty()) {
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
*/
}