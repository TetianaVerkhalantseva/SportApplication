package com.example.sportapplication.ui.inventory

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportapplication.database.entity.ItemCategory
import com.example.sportapplication.database.entity.ItemType
import com.example.sportapplication.database.model.InventoryItem
import com.example.sportapplication.database.model.Item
import com.example.sportapplication.database.model.itemCategoryToDrawable
import java.util.Date


@Composable
fun ItemUI(
    item: Item,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
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

