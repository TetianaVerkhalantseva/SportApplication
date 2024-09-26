package com.example.sportapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.sportapplication.ui.main.navigation.MAIN_ROUTE
import com.example.sportapplication.ui.main.navigation.mainScreen
import com.example.sportapplication.ui.main.navigation.navigateToMain
import com.example.sportapplication.ui.second.navigation.navigateToSecond
import com.example.sportapplication.ui.second.navigation.secondScreen

private const val GENERAL_ROUTE = "GENERAL_ROUTE"

@Composable
fun AppNavHost(navHostController: NavHostController) {

    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = MAIN_ROUTE,
        route = GENERAL_ROUTE
    ) {
        mainScreen(
            navHostController = navHostController,
            navigateToSecondScreen = { navHostController.navigateToSecond() }
        )
        secondScreen(
            navHostController = navHostController,
            navigateToMainScreen = { navHostController.navigateToMain() }
        )
    }

}