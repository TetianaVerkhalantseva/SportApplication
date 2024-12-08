package com.example.sportapplication.ui.activity.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.sportapplication.ui.about.navigation.aboutUsScreen
import com.example.sportapplication.ui.achievements.navigation.achievementsScreen
import com.example.sportapplication.ui.event.navigation.eventScreen
import com.example.sportapplication.ui.event.selectedEvent.navigation.navigateToSelectedEvent
import com.example.sportapplication.ui.event.selectedEvent.navigation.selectedEventScreen
import com.example.sportapplication.ui.inventory.navigation.inventoryScreen
import com.example.sportapplication.ui.map.navigation.MAP_ROUTE
import com.example.sportapplication.ui.map.navigation.mapRoute
import com.example.sportapplication.ui.profile.navigation.profileRoute
import com.example.sportapplication.ui.quest.navigation.questScreen
import com.example.sportapplication.ui.quest.selectedQuest.navigation.navigateToSelectedQuest
import com.example.sportapplication.ui.quest.selectedQuest.navigation.selectedQuestScreen

private const val GENERAL_ROUTE = "general_route"

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    setBottomBarVisibility: (Boolean) -> Unit,
    setSettingsVisibility: (Boolean) -> Unit
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = MAP_ROUTE,
        route = GENERAL_ROUTE
    ) {
        // Definer alle rutene
        mapRoute(navHostController, setBottomBarVisibility, setSettingsVisibility)
        questScreen(navHostController) { navHostController.navigateToSelectedQuest(it) }
        achievementsScreen(navHostController)
        inventoryScreen(navHostController)
        profileRoute(navHostController)
        aboutUsScreen(navHostController)
        eventScreen(navHostController) { navHostController.navigateToSelectedEvent(it) }
        selectedQuestScreen(navHostController)
        selectedEventScreen(navHostController)
    }
}
