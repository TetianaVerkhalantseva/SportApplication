package com.example.sportapplication.ui.inventory


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.database.model.InventoryItem
import com.example.sportapplication.database.model.Item

@Composable
fun InventoryScreenRoute() {

    val viewModel: InventoryViewModel = hiltViewModel()
    val inventoryItems = viewModel.inventoryItems
    val itemList = viewModel.items
    InventoryScreen(inventoryItems, itemList, viewModel)
}

@Composable
fun InventoryScreen(
    inventoryItems: SnapshotStateList<InventoryItem>,
    itemList:SnapshotStateList<Item>,
    viewModel: InventoryViewModel
) {
    var inventory = inventoryItems.toMutableList()
    var items = itemList.toMutableList()

    Column {

        CategoryItem(
            text = "Inventory",
            isAnyCategoryItemSelected = inventory.any { it.isSelected },
            doOnSelectAll = {
                inventory = inventory.map {
                    it.copy(
                        isSelected = true
                    )
                }.toMutableList()
            },
            doOnClearAll = {
                inventory = inventory.map {
                    it.copy(
                        isSelected = false
                    )
                }.toMutableList()
            }
        )

        InventoryLazyColumn(
            items = inventory,
            doOnItemSelect = { itemToSelect, boolean ->
                inventory = inventory.map {
                    it.copy(
                        isSelected =
                        if (it == itemToSelect) boolean
                        else it.isSelected
                    )
                }.toMutableList()
            }
        )
        /*Button(onClick = { viewModel.addToInventory(itemList[0]) }) {
            Text("Add Item")
        }*/

        CategoryItem(
            text = "Item",
            isAnyCategoryItemSelected = items.any { it.isSelected },
            doOnSelectAll = {
                items = items.map {
                    it.copy(
                        isSelected = true
                    )
                }.toMutableList()
            },
            doOnClearAll = {
                items = items.map {
                    it.copy(
                        isSelected = false
                    )
                }.toMutableList()
            }
        )

        ItemLazyColumn(
            items = items,
            doOnItemSelect = { itemToSelect, boolean ->
                items = items.map {
                    it.copy(
                        isSelected =
                        if (it == itemToSelect) boolean
                        else it.isSelected
                    )
                }.toMutableList()
            }
        )
    }
}

@Composable
fun CategoryItem(
    text: String,
    isAnyCategoryItemSelected: Boolean,
    doOnSelectAll: () -> Unit,
    doOnClearAll: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.DarkGray)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1F))
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        if (isAnyCategoryItemSelected) doOnClearAll()
                        else doOnSelectAll()
                    },
                text =
                if (isAnyCategoryItemSelected) "CLEAR ALL"
                else "SELECT ALL",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.DarkGray)
        )
    }
}

@Composable
fun InventoryLazyColumn(
    items: List<InventoryItem>,
    doOnItemSelect: (InventoryItem, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items) { item ->
            InventoryItemUI(
                item = item,
                doOnItemSelect = doOnItemSelect
            )
        }
    }
}

@Composable
fun ItemLazyColumn(
    items: List<Item>,
    doOnItemSelect: (Item, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items) { item ->
            ItemUI(
                item = item,
                doOnItemSelect = doOnItemSelect
            )
        }
    }
}

@Composable
fun InventoryItemUI(
    item: InventoryItem,
    doOnItemSelect: (InventoryItem, Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {

        Row {
            if (item.image != null) Image(
                modifier = Modifier
                    .weight(1F)
                    .size(100.dp),
                imageVector = ImageVector.vectorResource(id = item.image),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier
                    .weight(2F)
                    .align(Alignment.CenterVertically),
                text = item.itemName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            if (item.itemId == -1L) Checkbox(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                checked = item.isSelected,
                onCheckedChange = { doOnItemSelect(item, it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red,
                    checkmarkColor = Color.White
                ),
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
    }
}


@Composable
fun ItemUI(
    item: Item,
    doOnItemSelect: (Item, Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {

        Row {
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier
                    .weight(2F)
                    .align(Alignment.CenterVertically),
                text = item.itemName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            if (item.itemId != -1L) Checkbox(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                checked = item.isSelected,
                onCheckedChange = { doOnItemSelect(item, it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red,
                    checkmarkColor = Color.White
                ),
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
    }
}
/*
val inventoryItems = listOf<InventoryItem>(
    InventoryItem(
        id = 1,
        image = R.drawable.ic_launcher_background,
        name = R.string.no_equipment_quests,
        type = InventoryType.RACKS_AND_BENCHES
    ),
    InventoryItem(
        id = 2,
        image = R.drawable.ic_launcher_foreground,
        name = R.string.no_equipment_quests,
        type = InventoryType.RESISTANCE_BANDS
    ),
    InventoryItem(
        id = 3,
        image = R.drawable.ic_launcher_background,
        name = R.string.no_equipment_quests,
        type = InventoryType.RACKS_AND_BENCHES
    ),
    InventoryItem(
        id = 4,
        image = R.drawable.ic_launcher_background,
        name = R.string.no_equipment_quests,
        type = InventoryType.RESISTANCE_BANDS
    ),
)*/