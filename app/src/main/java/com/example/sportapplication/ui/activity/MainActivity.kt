package com.example.sportapplication.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.sportapplication.ui.activity.main.MainScreen
import com.example.sportapplication.ui.activity.navigation.AppNavHost
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
                    val viewModel : MainActivityViewModel = hiltViewModel()
                    val navHostController = rememberNavController()

                    MainScreen(navController = navHostController)

                    LaunchedEffect(key1 = viewModel.user) {
                       // if (viewModel.user != null) navHostController.navigateToMain()
                    }
                }
            }
        }
    }
}