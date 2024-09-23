package com.example.sportapplication.ui.second.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.second.SecondScreenRoute

const val SECOND_ROUTE = "second_route"

fun NavController.navigateToSecond(navOptions: NavOptions? = null) =
    navigate(SECOND_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.secondScreen(
    navHostController: NavHostController,
    navigateToMainScreen: () -> Unit,
) {
    composable(
        route = SECOND_ROUTE
    ) {
        SecondScreenRoute(
            navigateToMainScreen = navigateToMainScreen
        )
    }
}