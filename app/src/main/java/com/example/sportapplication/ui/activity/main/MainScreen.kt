package com.example.sportapplication.ui.activity.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.sportapplication.ui.activity.navigation.AppNavHost

@Composable
fun MainScreen(
    navController: NavHostController,
    showBottomBar: Boolean
) {

    Scaffold(
        bottomBar = {
            if (showBottomBar) BottomNavBar(navController = navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ) {
            AppNavHost(navHostController = navController)
        }
    }
}