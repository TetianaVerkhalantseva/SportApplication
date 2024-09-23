package com.example.sportapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sportapplication.ui.mainScreen.MainScreenRoute
import com.example.sportapplication.ui.mainScreen.navigation.MAIN_ROUTE
import com.example.sportapplication.ui.mainScreen.navigation.navigateToMain
import com.example.sportapplication.ui.secondScreen.SecondScreenRoute
import com.example.sportapplication.ui.secondScreen.navigation.SECOND_ROUTE
import com.example.sportapplication.ui.secondScreen.navigation.navigateToSecond

private const val GENERAL_ROUTE = "GENERAL_ROUTE"

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = MAIN_ROUTE,
        route = GENERAL_ROUTE
    ) {
        composable(
            route = MAIN_ROUTE
        ) {
            MainScreenRoute(
                navigateToSecondScreen = { navController.navigateToSecond() }
            )
        }
        composable(
            route = SECOND_ROUTE
        )
        {
            SecondScreenRoute(
                navigateToMainScreen = { navController.navigateToMain() }
            )
        }
    }

}