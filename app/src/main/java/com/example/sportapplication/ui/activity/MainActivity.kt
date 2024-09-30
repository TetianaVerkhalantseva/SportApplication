package com.example.sportapplication.ui.activity

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.sportapplication.ui.introduction.navigation.INTRODUCTION_ROUTE
import com.example.sportapplication.ui.theme.SportApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val isBottomBarVisible = remember { mutableStateOf(true) }
                    val viewModel : MainActivityViewModel = hiltViewModel()
                    val navHostController = rememberNavController()

                    navHostController.addOnDestinationChangedListener { controller, destination, arguments ->
                        isBottomBarVisible.value =
                            destination.route != INTRODUCTION_ROUTE
                    }

                    MainScreen(
                        navController = navHostController,
                        showBottomBar = isBottomBarVisible.value
                    )
                }
            }
        }
    }
}