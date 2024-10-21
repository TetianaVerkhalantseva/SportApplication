package com.example.sportapplication.ui.activity

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.sportapplication.ui.achievements.selectedAchievement.navigation.SELECTED_ACHIEVEMENT_ROUTE_WITH_ARGUMENTS
import com.example.sportapplication.ui.introduction.navigation.INTRODUCTION_ROUTE
import com.example.sportapplication.ui.theme.SportApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configure OSM caching to reduce map data loading by storing tiles locally
        val osmConfig = Configuration.getInstance()
        osmConfig.userAgentValue = packageName
        osmConfig.osmdroidBasePath = File(cacheDir, "osmdroid")
        osmConfig.osmdroidTileCache = File(cacheDir, "osmdroid/tiles")

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
                                    && destination.route != SELECTED_ACHIEVEMENT_ROUTE_WITH_ARGUMENTS
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