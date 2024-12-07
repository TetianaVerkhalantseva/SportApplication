package com.example.sportapplication.database.data


import com.example.sportapplication.database.dao.InventoryDao
import com.example.sportapplication.database.dao.ItemsDao
import com.example.sportapplication.database.entity.InventoryData
import com.example.sportapplication.database.entity.ItemCategory
import com.example.sportapplication.database.entity.ItemType
import com.example.sportapplication.database.entity.ItemsData
import com.example.sportapplication.database.model.InventoryItem
import javax.inject.Inject

class ItemRepository @Inject constructor(private val itemsDao: ItemsDao, private val inventoryDao: InventoryDao){
    suspend fun prepopulateItems(){
        val items = itemsDao.getAll()

        if (items.isEmpty()) {
            val prepopulateItems = arrayOf(
                ItemsData(itemId = 0, itemName = "PowerAid Drink", itemType = ItemType.ACTIVE, itemCategory = ItemCategory.DRINK),
                ItemsData(itemId = 0, itemName = "Energizer Bar", itemType = ItemType.ACTIVE, itemCategory = ItemCategory.CHOCOLATEBAR),
                ItemsData(itemId = 0, itemName = "Comfortable Sneakers", itemType = ItemType.PASSIVE, itemCategory = ItemCategory.SNEAKER),
            )
            prepopulateItems.forEach { item ->
                itemsDao.insertItem(
                    item
                )
            }

        }
    }

    //ITEM

    suspend fun getAllItems(): List<ItemsData>{
        return itemsDao.getAll()
    }

    suspend fun getItemById(id: Int): ItemsData{
        return itemsDao.getItemById(id)
    }


    //INVENTORY

    suspend fun getAllInventory(): List<InventoryItem> {
        return inventoryDao.getAll().map{ inventoryItem -> InventoryItem(inventoryId = inventoryItem.inventoryId, itemId = inventoryItem.itemId, itemName = inventoryItem.itemName, itemType = inventoryItem.itemType, itemCategory = inventoryItem.itemCategory)}.toList()
    }

    suspend fun insertItemToInventory(item: ItemsData): List<InventoryItem> {
        inventoryDao.insertItem(InventoryData(inventoryId = 0, itemId = item.itemId, itemName = item.itemName, itemType = item.itemType, itemCategory=item.itemCategory))
        return inventoryDao.getAll().map{ inventoryItem -> InventoryItem(inventoryId = inventoryItem.inventoryId, itemId = inventoryItem.itemId, itemName = inventoryItem.itemName, itemType = inventoryItem.itemType, itemCategory = inventoryItem.itemCategory)}.toList()
    }

    suspend fun getInventoryItemById(id: Long): InventoryItem {
        val inventoryData = inventoryDao.getInventoryDataById(id)
        return InventoryItem(inventoryId = inventoryData.inventoryId, itemId = inventoryData.itemId, itemName = inventoryData.itemName, itemType = inventoryData.itemType, itemCategory = inventoryData.itemCategory)
    }

    suspend fun removeItemFromInventory(inventoryId: Long): List<InventoryItem>{
        val existingItem = inventoryDao.getInventoryDataById(inventoryId)
        inventoryDao.deleteItem(existingItem);
        return inventoryDao.getAll().map{ inventoryItem -> InventoryItem(inventoryId = inventoryItem.inventoryId, itemId = inventoryItem.itemId, itemName = inventoryItem.itemName, itemType = inventoryItem.itemType, itemCategory = inventoryItem.itemCategory)}.toList()
    }




}