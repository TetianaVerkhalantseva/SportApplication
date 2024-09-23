package com.example.sportapplication.ui.secondScreen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.mainScreen.navigation.MAIN_ROUTE
import com.example.sportapplication.ui.secondScreen.SecondScreenRoute

const val SECOND_ROUTE = "second_route"

fun NavController.navigateToSecond(navOptions: NavOptions? = null) =
    navigate(SECOND_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.secondScreen(
    navHostController: NavHostController,
    navigateToMainScreen: () -> Unit,
) {
    composable(
        route = MAIN_ROUTE
    ) {
        SecondScreenRoute(
            navigateToMainScreen = navigateToMainScreen
        )
    }
}