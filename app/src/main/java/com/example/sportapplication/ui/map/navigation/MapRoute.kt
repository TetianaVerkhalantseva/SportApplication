package com.example.sportapplication.ui.map.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.map.MapScreenRoute

const val MAP_ROUTE = "map_route"

fun NavController.navigateToMap(navOptions: NavOptions? = null) =
    navigate(MAP_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.mapRoute(
    navHostController: NavHostController,
) {
    composable(
        route = MAP_ROUTE
    ) {
        MapScreenRoute(
            //navigateToMainScreen = { navHostController.navigateToMain() }
        )
    }
}