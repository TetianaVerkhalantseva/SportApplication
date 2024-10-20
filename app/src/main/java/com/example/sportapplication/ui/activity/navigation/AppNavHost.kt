package com.example.sportapplication.ui.activity.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.example.sportapplication.ui.achievements.navigation.achievementsScreen
import com.example.sportapplication.ui.achievements.selectedAchievement.navigation.navigateToSelectedAchievement
import com.example.sportapplication.ui.achievements.selectedAchievement.navigation.selectedAchievementScreen
import com.example.sportapplication.ui.introduction.navigation.INTRODUCTION_ROUTE
import com.example.sportapplication.ui.introduction.navigation.introductionScreen
import com.example.sportapplication.ui.inventory.navigation.inventoryScreen
import com.example.sportapplication.ui.map.navigation.mapRoute
import com.example.sportapplication.ui.map.navigation.navigateToMap
import com.example.sportapplication.ui.profile.navigation.profileRoute // Import profileRoute
import com.example.sportapplication.ui.quest.navigation.questScreen
import com.example.sportapplication.ui.quest.selectedQuest.navigation.navigateToSelectedQuest
import com.example.sportapplication.ui.quest.selectedQuest.navigation.selectedQuestScreen
import com.example.sportapplication.ui.sensor.navigation.sensorScreen

private const val GENERAL_ROUTE = "GENERAL_ROUTE"

@Composable
fun AppNavHost(navHostController: NavHostController) {

    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = INTRODUCTION_ROUTE,
        route = GENERAL_ROUTE
    ) {
        introductionScreen(
            navHostController = navHostController,
            navigateToMapScreen = {
                navHostController.navigateToMap(
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(route = INTRODUCTION_ROUTE, inclusive = true, saveState = false)
                        .build()
                )
            }
        )
        questScreen(
            navHostController = navHostController,
            navigateToSelectedQuestScreen = { navHostController.navigateToSelectedQuest() }
        )
        mapRoute(
            navHostController = navHostController
        )
        achievementsScreen(
            navHostController = navHostController,
            navigateToSelectedAchievementScreen = {
                navHostController.navigateToSelectedAchievement(it.uid)
            }
        )
        inventoryScreen(
            navHostController = navHostController
        )
        selectedAchievementScreen(
            navHostController = navHostController
        )
        selectedQuestScreen(
            navHostController = navHostController
        )
        sensorScreen(navHostController = navHostController)

        profileRoute(navHostController = navHostController)
    }
}
