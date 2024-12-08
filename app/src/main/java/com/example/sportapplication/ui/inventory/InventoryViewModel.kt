package com.example.sportapplication.ui.inventory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.repository.ItemRepository
import com.example.sportapplication.database.entity.ItemCategory
import com.example.sportapplication.database.entity.ItemType
import com.example.sportapplication.database.model.InventoryItem
import com.example.sportapplication.database.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    private var activeTimer: Timer = Timer()
    private var isTimerActive: Boolean = false
    var currentTime by mutableLongStateOf(System.currentTimeMillis())

    private val emptyInventoryItem = InventoryItem(
        inventoryId = -1,
        itemId = -1,
        itemName = "No Items",
        itemCategory = ItemCategory.PLACEHOLDER,
        itemType = ItemType.INAPPLICABLE, itemActivated = null, itemDuration = null, itemEffectOnXp = null, itemEffectOnDuration = null

    )

    private val emptyItemsItem = Item(
        itemId = -1,
        itemName = "No Items",
        itemType = ItemType.INAPPLICABLE,
        itemCategory = ItemCategory.PLACEHOLDER,
        itemActivated = null,
        itemDuration = null,
        itemEffectOnXp = null,
        itemEffectOnDuration = null
    )

    var inventoryItems =
        mutableStateListOf(
            emptyInventoryItem
        )

    var items =
        mutableStateListOf(
            emptyItemsItem
        )

    val activeInventoryItems: MutableList<InventoryItem> = mutableListOf()

    init {
        viewModelScope.launch(){
            itemRepository.prepopulateItems()

            items.clear()
            itemRepository.getAllItems().forEach{
                item -> items.add(item)
            }


            val inventoryInDao = itemRepository.getAllInventory()
            if(inventoryInDao.isNotEmpty()){
                inventoryItems.clear()
                inventoryInDao.forEach{


                    inventoryItems.add(it)
                }
            }

            val activeInDao = itemRepository.getAllActiveInventoryItems()
            if(activeInDao.isNotEmpty()){
                activeInventoryItems.clear()
                activeInDao.forEach { activeInventoryItems.add(it) }
                activeTimer = setFixedRateTimerActiveInventory()
                isTimerActive = true
            }

        }
    }

    fun addItemToInventoryById(itemId: Long){
        viewModelScope.launch {

            val updatedInventory = itemRepository.insertItemToInventory(itemId)

            if(updatedInventory.isNotEmpty()){
                inventoryItems.clear()
                updatedInventory.forEach { inventoryItem -> inventoryItems.add(inventoryItem) }
            }


        }
    }

    fun removeItemFromInventoryById(inventoryId: Long){
        viewModelScope.launch {
            val isActive = activeInventoryItems.find {inventoryItem -> inventoryId == inventoryItem.inventoryId}

            val updatedInventory = itemRepository.removeItemFromInventory(inventoryId)

            inventoryItems.clear()
            if(updatedInventory.isEmpty()){
                inventoryItems.add(emptyInventoryItem)
            } else{
                updatedInventory.forEach{inventoryItem -> inventoryItems.add(inventoryItem)}
            }

            if(isActive != null) {
                activeInventoryItems.clear()
                val activeUpdate = itemRepository.getAllActiveInventoryItems()
                if (activeUpdate.isNotEmpty()) activeUpdate.forEach { activeInventoryItems.add(
                    it
                ) }
            }

        }
    }

    fun activateItemInInventoryById(inventoryId: Long){
        viewModelScope.launch {
            val updatedInventory = itemRepository.updateInventoryItem(inventoryId, System.currentTimeMillis())
            val activeInventoryItemsFromRepository = itemRepository.getAllActiveInventoryItems()

            activeInventoryItems.clear()
            activeInventoryItemsFromRepository.forEach { activeInventoryItems.add(
                it
            ) }

            if(!isTimerActive){
                isTimerActive = true
                activeTimer = setFixedRateTimerActiveInventory()

            }

            inventoryItems.clear()

            updatedInventory.forEach{inventoryItem -> inventoryItems.add(inventoryItem)}

        }
    }

    private fun setFixedRateTimerActiveInventory(): Timer {
        return fixedRateTimer("activeInventoryItemsTimer", true, 1000L, 1000L){
            updateActiveInventoryItems()}
    }

    private fun updateActiveInventoryItems(){
        if(activeInventoryItems.isNotEmpty()){
            currentTime = System.currentTimeMillis()
            activeInventoryItems.forEach{ if(it.itemActivated!! + it.itemDuration!! - currentTime < 0){
                removeItemFromInventoryById(it.inventoryId)
            } }
        } else {
            currentTime = System.currentTimeMillis()
            isTimerActive = false
            activeTimer.cancel()
        }

    }


}