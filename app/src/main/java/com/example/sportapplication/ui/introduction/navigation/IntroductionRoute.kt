package com.example.sportapplication.ui.introduction.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.introduction.IntroductionScreenRoute

const val INTRODUCTION_ROUTE = "introduction_route"

fun NavController.navigateToIntroduction(navOptions: NavOptions? = null) =
    navigate(INTRODUCTION_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.introductionScreen(
    navHostController: NavHostController,
    navigateToMapScreen: () -> Unit,
) {
    composable(
        route = INTRODUCTION_ROUTE
    ) {
        IntroductionScreenRoute(
            navigateToMapScreen = navigateToMapScreen
        )
    }
}