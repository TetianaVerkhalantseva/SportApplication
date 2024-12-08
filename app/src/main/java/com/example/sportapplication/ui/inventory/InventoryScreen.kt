package com.example.sportapplication.ui.inventory


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.sportapplication.database.entity.ItemType
import com.example.sportapplication.database.model.InventoryItem
import com.example.sportapplication.database.model.Item
import com.example.sportapplication.database.model.itemCategoryToDrawable
import java.util.Date
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
        item{Button(onClick = { viewModel.addItemToInventoryById(Random.nextInt(1,4)) }) {
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

@Composable
fun InventoryItemUI(
    item: InventoryItem,
    currentTime: Long,
    removeItemFunction: (inventoryId: Long) -> Unit,
    activateItemFunction: (inventoryId: Long) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
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
                text = item.itemName, softWrap = true,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            if (item.itemId != -1L) {
                Box(Modifier.padding(start = 4.dp, end = 4.dp)){
                    if(item.itemType == ItemType.ACTIVE) {
                        Button(onClick = { activateItemFunction(item.inventoryId) }, enabled = item.itemActivated == null) {
                            Text("Use")
                        }
                    } else if(item.itemType == ItemType.PASSIVE){
                        Text("Effect", color = Color(24, 84, 181), modifier = Modifier.clickable { Log.i("test effect item", "clicked effect item") })
                    }
                }

                Button(onClick={removeItemFunction(item.inventoryId)}){
                    Text("Drop")
                }
            }

        }
        if(item.itemActivated != null){
            Column{
                Text("Activated: ${Date(item.itemActivated)}\nDuration: ${item.itemDuration?.div(
                    60_000L
                )} minutes")
                Text("Remaining: ${millisToHours(item.itemDuration?.plus(item.itemActivated)?.minus(currentTime)?: 0L)}")
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

        Row(verticalAlignment = Alignment.CenterVertically) {
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
            if (item.itemCategory != ItemCategory.PLACEHOLDER) Image(
                modifier = Modifier
                    .weight(1F)
                    .size(50.dp),
                painter = painterResource(itemCategoryToDrawable(item.itemCategory)),
                contentDescription = null
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

@SuppressLint("DefaultLocale")
fun millisToHours(millis: Long): String{
    return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
        TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
        TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1))
}