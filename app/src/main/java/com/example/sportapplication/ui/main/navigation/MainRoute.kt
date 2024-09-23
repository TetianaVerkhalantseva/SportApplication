package com.example.sportapplication.ui.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.main.MainScreenRoute

const val MAIN_ROUTE = "main_route"

fun NavController.navigateToMain(navOptions: NavOptions? = null) =
    navigate(MAIN_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.mainScreen(
    navHostController: NavHostController,
    navigateToSecondScreen: () -> Unit,
) {
    composable(
        route = MAIN_ROUTE
    ) {
        MainScreenRoute(
            navigateToSecondScreen = navigateToSecondScreen
        )
    }
}