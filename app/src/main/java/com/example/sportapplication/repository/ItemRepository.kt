package com.example.sportapplication.repository


import com.example.sportapplication.database.dao.InventoryDao
import com.example.sportapplication.database.dao.ItemsDao
import com.example.sportapplication.database.entity.InventoryData
import com.example.sportapplication.database.entity.ItemCategory
import com.example.sportapplication.database.entity.ItemType
import com.example.sportapplication.database.entity.ItemsData
import com.example.sportapplication.database.model.InventoryItem
import com.example.sportapplication.database.model.Item
import javax.inject.Inject

class ItemRepository @Inject constructor(private val itemsDao: ItemsDao, private val inventoryDao: InventoryDao){
    suspend fun prepopulateItems(){
        itemsDao.deleteAll()
        itemsDao.resetPrimaryKey()
        val items = itemsDao.getAll()

        if (items.isEmpty()) {
            val prepopulateItems = arrayOf(
                ItemsData(itemId = 0, itemName = "PowerAid Drink", itemType = ItemType.ACTIVE, itemCategory = ItemCategory.DRINK, itemActivated = null, itemDuration = minutesToMilliseconds(30L), itemEffectOnXp = 1.25f, itemEffectOnDuration = null),
                ItemsData(itemId = 0, itemName = "Energizer Bar", itemType = ItemType.ACTIVE, itemCategory = ItemCategory.CHOCOLATEBAR, itemActivated = null, itemDuration = minutesToMilliseconds(15L), itemEffectOnXp = 1.25f, itemEffectOnDuration = null),
                ItemsData(itemId = 0, itemName = "Comfortable Sneakers", itemType = ItemType.PASSIVE, itemCategory = ItemCategory.SNEAKER, itemActivated = null, itemDuration = null, itemEffectOnXp = null, itemEffectOnDuration = 1.25f),
            )
            prepopulateItems.forEach { item ->
                itemsDao.insertItem(
                    item
                )
            }
        }
    }

    //ITEM

    suspend fun getAllItems(): List<Item>{
        return itemsDao.getAll().map {itemsData -> convertItemsDataToItem(itemsData)}.toList()
    }

    suspend fun getItemById(id: Int): Item{
        return convertItemsDataToItem(itemsDao.getItemById(id))
    }


    //INVENTORY
        // GET FUNCTIONS
    suspend fun getAllInventory(): List<InventoryItem> {
        return inventoryDao.getAll().map{ inventoryData -> convertInventoryDataToInventoryItem(inventoryData)}.toList()
    }

    suspend fun getInventoryItemById(id: Long): InventoryItem {
        val inventoryData = inventoryDao.getInventoryDataById(id)
        return convertInventoryDataToInventoryItem(inventoryData)
    }

    suspend fun getAllActiveInventoryItems(): List<InventoryItem> {
        val allItems = inventoryDao.getAll()
        val activatedItems = allItems.filter { inventoryData -> inventoryData.itemType == ItemType.ACTIVE }

        return activatedItems.map{ inventoryData -> convertInventoryDataToInventoryItem(inventoryData)}.toList()

    }

    suspend fun getAllPassiveInventoryItems(): List<InventoryItem>{
        val allItems = inventoryDao.getAll()
        val passiveItems = allItems.filter { inventoryData -> inventoryData.itemType == ItemType.PASSIVE}

        return passiveItems.map { inventoryData -> convertInventoryDataToInventoryItem(inventoryData)}.toList()
    }

    // INSERTS
    suspend fun insertItemToInventory(itemId: Int): List<InventoryItem> {
        val item = itemsDao.getItemById(itemId)
        inventoryDao.insertItem(convertItemsDataToInventoryData(item))
        return inventoryDao.getAll().map{ inventoryData -> convertInventoryDataToInventoryItem(inventoryData)}.toList()
    }

    // REMOVES
    suspend fun removeItemFromInventory(inventoryId: Long): List<InventoryItem>{
        val existingItem = inventoryDao.getInventoryDataById(inventoryId)
        inventoryDao.deleteItem(existingItem)
        return inventoryDao.getAll().map{ inventoryData -> convertInventoryDataToInventoryItem(inventoryData)}.toList()
    }

    //UPDATES
    suspend fun updateInventoryItem(inventoryId: Long, activationTime: Long): List<InventoryItem>{
        val existingItem = inventoryDao.getInventoryDataById(inventoryId)
        existingItem.itemActivated = activationTime
        inventoryDao.updateItem(existingItem)
        return inventoryDao.getAll().map{ inventoryData -> convertInventoryDataToInventoryItem(inventoryData)}.toList()
    }




    // CONVERSIONS
    private fun convertInventoryDataToInventoryItem(inventoryData: InventoryData): InventoryItem {
        return InventoryItem(
            inventoryId = inventoryData.inventoryId, itemId = inventoryData.itemId, itemName = inventoryData.itemName, itemType = inventoryData.itemType,
            itemCategory = inventoryData.itemCategory, itemActivated = inventoryData.itemActivated, itemDuration = inventoryData.itemDuration,
            itemEffectOnXp = inventoryData.itemEffectOnXp, itemEffectOnDuration = inventoryData.itemEffectOnDuration
        )
    }

    // OBS THIS CAN ONLY BE USED FOR NEW INVENTORYITEM AS IT SETS INVENTORYID TO 0
    private fun convertItemsDataToInventoryData(itemData: ItemsData): InventoryData {
        return InventoryData(inventoryId = 0, itemId = itemData.itemId, itemName = itemData.itemName, itemType = itemData.itemType,
            itemCategory= itemData.itemCategory, itemActivated = itemData.itemActivated, itemDuration = itemData.itemDuration,
            itemEffectOnXp = itemData.itemEffectOnXp, itemEffectOnDuration = itemData.itemEffectOnDuration
        )
    }

    private fun convertItemsDataToItem(itemData: ItemsData): Item {
        return Item(itemId = itemData.itemId, itemName = itemData.itemName, itemType = itemData.itemType,
        itemCategory= itemData.itemCategory, itemActivated = itemData.itemActivated, itemDuration = itemData.itemDuration,
        itemEffectOnXp = itemData.itemEffectOnXp, itemEffectOnDuration = itemData.itemEffectOnDuration
        )
    }

    private fun minutesToMilliseconds(minutes: Long):Long{
        return minutes * 60_000L
    }
}