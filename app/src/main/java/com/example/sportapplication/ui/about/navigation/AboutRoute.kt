package com.example.sportapplication.ui.about.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.about.AboutUsScreen

const val ABOUT_US_ROUTE = "about_us_route"

fun NavController.navigateToAboutUs(navOptions: NavOptions? = null) =
    navigate(ABOUT_US_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.aboutUsScreen(
    navHostController: NavHostController
) {
    composable(
        route = ABOUT_US_ROUTE
    ) {
        AboutUsScreen()
    }
}
