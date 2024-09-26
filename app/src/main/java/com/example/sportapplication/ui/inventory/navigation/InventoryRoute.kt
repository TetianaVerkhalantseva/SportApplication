package com.example.sportapplication.ui.inventory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.inventory.InventoryScreenRoute

const val INVENTORY_ROUTE = "inventory_route"

fun NavController.navigateToInventory(navOptions: NavOptions? = null) =
    navigate(INVENTORY_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.inventoryScreen(
    navHostController: NavHostController
) {
    composable(
        route = INVENTORY_ROUTE
    ) {
        InventoryScreenRoute()
    }
}