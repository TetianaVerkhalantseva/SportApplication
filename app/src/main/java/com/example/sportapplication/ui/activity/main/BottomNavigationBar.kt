package com.example.sportapplication.ui.activity.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sportapplication.R
import com.example.sportapplication.ui.achievements.navigation.ACHIEVEMENTS_ROUTE
import com.example.sportapplication.ui.event.navigation.EVENT_ROUTE
import com.example.sportapplication.ui.inventory.navigation.INVENTORY_ROUTE
import com.example.sportapplication.ui.map.navigation.MAP_ROUTE
import com.example.sportapplication.ui.model.BottomNavItem
import com.example.sportapplication.ui.quest.navigation.QUEST_ROUTE

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = remember {
        listOf(
            BottomNavItem(R.string.map_screen, R.drawable.location_on_24px, MAP_ROUTE),
            BottomNavItem(R.string.events, R.drawable.event_icon, EVENT_ROUTE),
            BottomNavItem(R.string.quest_screen, R.drawable.question_mark_24px, QUEST_ROUTE),
            BottomNavItem(R.string.inventory_screen, R.drawable.inventory_2_24px, INVENTORY_ROUTE),
            BottomNavItem(R.string.achievements_screen, R.drawable.emoji_events_24px, ACHIEVEMENTS_ROUTE)
        )
    }

    NavigationBar(
        navController = navController,
        items = items
    )
}

@Composable
fun NavigationBar(navController: NavHostController, items: List<BottomNavItem>) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentSelectedRoute = currentBackStack?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        items.forEach { item ->
            val isSelected = currentSelectedRoute == item.route
            val iconScale by animateFloatAsState(if (isSelected) 1.2f else 1f)

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(MAP_ROUTE) { inclusive = false } // Peker alltid til MAP_ROUTE
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .graphicsLayer {
                                    scaleX = iconScale
                                    scaleY = iconScale
                                },
                            tint = if (isSelected) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = stringResource(id = item.name),
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}
