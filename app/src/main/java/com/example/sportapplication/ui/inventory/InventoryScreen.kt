package com.example.sportapplication.ui.inventory


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.database.entity.ItemCategory
import com.example.sportapplication.database.model.InventoryItem
import com.example.sportapplication.database.model.Item
import com.example.sportapplication.database.model.itemCategoryToDrawable

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
    val inventory = inventoryItems.toMutableList()
    val items = itemList.toMutableList()

    Column {

        CategoryItem(
            text = "Inventory",
        )

        InventoryLazyColumn(
            items = inventory
        )
        Button(onClick = { viewModel.addItemToInventoryById(1) }) {
            Text("Add Item")
        }

        CategoryItem(
            text = "Item",

        )

        ItemLazyColumn(
            items = items
        )
    }
}

@Composable
fun CategoryItem(
    text: String,
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
    items: List<InventoryItem>
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items) { item ->
            InventoryItemUI(
                item = item
            )
        }
    }
}

@Composable
fun ItemLazyColumn(
    items: List<Item>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items) { item ->
            ItemUI(
                item = item
            )
        }
    }
}

@Composable
fun InventoryItemUI(
    item: InventoryItem
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {

        Row {
            if (item.itemCategory != ItemCategory.PLACEHOLDER) Image(
                modifier = Modifier
                    .weight(1F)
                    .size(50.dp),
                painter = painterResource(itemCategoryToDrawable(item.itemCategory)),
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
            if (item.itemId != -1L) {
                Button(onClick={Log.i("click test item", "clicked")}){
                    Text("TODO: Delete")
                }
            }

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
    item: Item
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
            if (item.itemId != -1L) {

                Log.i("test item", "item")
            }

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