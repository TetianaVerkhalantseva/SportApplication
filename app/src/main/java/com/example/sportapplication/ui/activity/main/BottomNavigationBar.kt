package com.example.sportapplication.ui.activity.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sportapplication.R
import com.example.sportapplication.ui.achievements.navigation.ACHIEVEMENTS_ROUTE
import com.example.sportapplication.ui.inventory.navigation.INVENTORY_ROUTE
import com.example.sportapplication.ui.map.navigation.MAP_ROUTE
import com.example.sportapplication.ui.model.BottomNavItem
import com.example.sportapplication.ui.quest.navigation.QUEST_ROUTE
import com.example.sportapplication.ui.sensor.navigation.SENSOR_ROUTE


@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    val items = remember {
        listOf(
            BottomNavItem(
                name = R.string.map_screen,
                icon = R.drawable.ic_launcher_foreground,
                route = MAP_ROUTE
            ),
            BottomNavItem(
                name = R.string.quest_screen,
                icon = R.drawable.ic_launcher_foreground,
                route = QUEST_ROUTE
            ),
            BottomNavItem(
                name = R.string.inventory_screen,
                icon = R.drawable.ic_launcher_foreground,
                route = INVENTORY_ROUTE
            ),
            BottomNavItem(
                name = R.string.achievements_screen,
                icon = R.drawable.ic_launcher_foreground,
                route = ACHIEVEMENTS_ROUTE
            ),
            BottomNavItem(
                name = R.string.sensor_screen,
                icon = R.drawable.ic_launcher_foreground,
                route = SENSOR_ROUTE
            )
        )
    }

    NavigationBar(navController = navController, items = items)
}

@Composable
fun NavigationBar(
    navController: NavHostController,
    items: List<BottomNavItem>
) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentSelectedRoute = currentBackStack?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        items.forEach { item ->
            val isSelected = currentSelectedRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
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
                            imageVector = ImageVector.vectorResource(id = item.icon),
                            contentDescription = null,
                            tint = if (isSelected) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(50.dp) // Icon size
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
                colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    }
}