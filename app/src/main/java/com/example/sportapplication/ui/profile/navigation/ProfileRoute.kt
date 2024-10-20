package com.example.sportapplication.ui.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.sportapplication.ui.profile.ProfileScreen

const val PROFILE_ROUTE = "profile"

fun NavGraphBuilder.profileRoute(
    navHostController: NavHostController
) {
    composable(route = PROFILE_ROUTE) {
        ProfileScreen(navController = navHostController)
    }
}

fun NavHostController.navigateToProfile() {
    this.navigate(PROFILE_ROUTE)
}
