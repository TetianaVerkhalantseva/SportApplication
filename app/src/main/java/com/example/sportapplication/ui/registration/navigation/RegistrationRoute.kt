package com.example.sportapplication.ui.registration.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.main.navigation.navigateToMain
import com.example.sportapplication.ui.registration.RegistrationScreenRoute

const val REGISTRATION_ROUTE = "registration_route"

fun NavController.navigateToRegistration(navOptions: NavOptions? = null) =
    navigate(REGISTRATION_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.registrationRoute(
    navHostController: NavHostController,
) {
    composable(
        route = REGISTRATION_ROUTE
    ) {
        RegistrationScreenRoute(
            navigateToMainScreen = { navHostController.navigateToMain() }
        )
    }
}