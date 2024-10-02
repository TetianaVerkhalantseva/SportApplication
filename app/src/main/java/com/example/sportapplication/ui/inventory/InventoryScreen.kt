package com.example.sportapplication.ui.inventory

import android.util.Log
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
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.R
import com.example.sportapplication.database.model.InventoryItem
import com.example.sportapplication.database.model.InventoryType

@Composable
fun InventoryScreenRoute() {

    val viewModel : InventoryViewModel = hiltViewModel()
}

@Composable
fun InventoryScreen() {
    val items = remember { mutableStateOf<List<InventoryItem>>(inventoryItems.toMutableList()) }
    Column {

        CategoryItem(
            text = "Racks and Benches",
            isAnyCategoryItemSelected = items.value.any { it.isSelected && it.type == InventoryType.RACKS_AND_BENCHES },
            doOnSelectAll = {
                            items.value = items.value.map {
                                it.copy(
                                    isSelected =
                                    if (it.type == InventoryType.RACKS_AND_BENCHES) true
                                    else it.isSelected
                                )
                            }
            },
            doOnClearAll = {
                items.value = items.value.map {
                    it.copy(
                        isSelected =
                            if (it.type == InventoryType.RACKS_AND_BENCHES) false
                            else it.isSelected
                    )
                }
            }
        )

        InventoryLazyColumn(
            items = items.value.filter { it.type == InventoryType.RACKS_AND_BENCHES },
                    doOnItemSelect = { itemToSelect, boolean ->
                        items.value = items.value.map {
                            it.copy(
                                isSelected =
                                    if (it == itemToSelect) boolean
                                    else it.isSelected
                            )
                        }
                    }
        )
        CategoryItem(
            text = "Resistance Bench",
            isAnyCategoryItemSelected = items.value.any { it.isSelected && it.type == InventoryType.RESISTANCE_BANDS },
            doOnSelectAll = {
                items.value = items.value.map {
                    it.copy(
                        isSelected =
                        if (it.type == InventoryType.RESISTANCE_BANDS) true
                        else it.isSelected
                    )
                }
            },
            doOnClearAll = {
                items.value = items.value.map {
                    it.copy(
                        isSelected =
                        if (it.type == InventoryType.RESISTANCE_BANDS) false
                        else it.isSelected
                    )
                }
            }
        )
        InventoryLazyColumn(
            items = items.value.filter { it.type == InventoryType.RESISTANCE_BANDS },
            doOnItemSelect = { itemToSelect, boolean ->
                items.value = items.value.map {
                    it.copy(
                        isSelected =
                        if (it == itemToSelect) boolean
                        else it.isSelected
                    )
                }
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
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
    ) {
        Spacer(modifier = Modifier
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

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.DarkGray)
        )


    }
}

@Composable
fun InventoryLazyColumn(
    items: List<InventoryItem>,
    doOnItemSelect : (InventoryItem, Boolean) -> Unit
    ) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items) {item ->
            InventoryItemUI(
                item = item,
                doOnItemSelect = doOnItemSelect
            )
        }
    }
}

@Composable
fun InventoryItemUI(
    item: InventoryItem,
    doOnItemSelect : (InventoryItem, Boolean) -> Unit
    ) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {


        Row {
            Image(
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
                text = stringResource(id = item.name),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Checkbox(
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
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray)
        )
    }
}

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
)