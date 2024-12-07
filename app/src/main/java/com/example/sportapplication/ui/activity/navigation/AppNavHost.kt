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


private const val GENERAL_ROUTE = "GENERAL_ROUTE"

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
        questScreen(
            navHostController = navHostController,
            navigateToSelectedQuestScreen = { navHostController.navigateToSelectedQuest(it) }
        )
        mapRoute(
            navHostController = navHostController,
            setBottomBarVisibility = setBottomBarVisibility,
            setSettingsVisibility = setSettingsVisibility
        )
        achievementsScreen(
            navHostController = navHostController
        )
        inventoryScreen(
            navHostController = navHostController
        )
        selectedQuestScreen(
            navHostController = navHostController
        )


        profileRoute(navHostController = navHostController)

        eventScreen(
            navHostController = navHostController,
            navigateToSelectedEventScreen = { navHostController.navigateToSelectedEvent(eventId = it) }
        )
        selectedEventScreen(
            navHostController = navHostController
        )
        aboutUsScreen(
            navHostController = navHostController
        )

    }
}