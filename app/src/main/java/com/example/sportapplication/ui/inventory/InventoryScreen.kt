package com.example.sportapplication.ui.inventory

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun InventoryScreenRoute() {

    val viewModel : InventoryViewModel = hiltViewModel()
}

@Composable
fun InventoryScreen() {
    Column {
        Text(text = "Inventory screen")
    }
}