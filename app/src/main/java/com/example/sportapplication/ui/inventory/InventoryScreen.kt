package com.example.sportapplication.ui.inventory


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.database.model.InventoryItem
import com.example.sportapplication.database.model.Item
import java.util.concurrent.TimeUnit
import kotlin.random.Random

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


    LazyColumn{

        item{CategoryItem(
            text = "Inventory",
        )}

        item{InventoryLazyColumn(
            items = inventory,
            currentTime = viewModel.currentTime,
            removeItemFunction = viewModel::removeItemFromInventoryById,
            activateItemFunction = viewModel::activateItemInInventoryById
        )}

        item{
            Spacer(Modifier.height(30.dp))
        }
        item{Button(onClick = { viewModel.addItemToInventoryById(Random.nextInt(1,4).toLong()) }) {
            Text("Add Item")
        }}

        item{CategoryItem(
            text = "Item Catalogue",

        )}

        item{ItemLazyColumn(
            items = items
        )}
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
    items: List<InventoryItem>,
    currentTime: Long,
    removeItemFunction: (inventoryId: Long) -> Unit,
    activateItemFunction: (inventoryId: Long) -> Unit

) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().heightIn(0.dp, 400.dp)
    ) {
        items(items) { item ->
            InventoryItemUI(
                item = item,
                currentTime,
                removeItemFunction,
                activateItemFunction,
            )
        }
    }
}

@Composable
fun ItemLazyColumn(
    items: List<Item>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().heightIn(0.dp, 400.dp)
    ) {
        items(items) { item ->
            ItemUI(
                item = item
            )
        }
    }
}






@SuppressLint("DefaultLocale")
fun millisToHours(millis: Long): String{
    return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
        TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
        TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1))
}