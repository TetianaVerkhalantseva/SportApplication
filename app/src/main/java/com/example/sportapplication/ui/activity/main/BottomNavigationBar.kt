package com.example.sportapplication.ui.activity.main

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.example.sportapplication.R
import com.example.sportapplication.ui.map.navigation.MAP_ROUTE
import com.example.sportapplication.ui.model.BottomNavItem

@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    val items = listOf(
        BottomNavItem(
            name = R.string.map_screen,
            icon = R.drawable.ic_launcher_foreground,
            route = MAP_ROUTE
        ),
        BottomNavItem(
            name = R.string.map_screen,
            icon = R.drawable.ic_launcher_foreground,
            route = MAP_ROUTE
        ),
        BottomNavItem(
            name = R.string.map_screen,
            icon = R.drawable.ic_launcher_foreground,
            route = MAP_ROUTE
        ),
        BottomNavItem(
            name = R.string.map_screen,
            icon = R.drawable.ic_launcher_foreground,
            route = MAP_ROUTE
        ),
    )

    BottomNavigationBar(navController = navController, items = items)
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<BottomNavItem>
) {
    val context = LocalContext.current
    BottomNavigation (
        backgroundColor = Color.Blue,
        contentColor = Color.Unspecified
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = false,
                onClick = { /*TODO*/ },
                icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = item.icon),
                    contentDescription = null
                )
            },
                label = {
                    Text(text = stringResource(id = item.name), color = Color.White)
                }
                )
        }

    }
}