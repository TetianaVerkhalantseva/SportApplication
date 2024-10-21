package com.example.sportapplication.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.sportapplication.ui.achievements.selectedAchievement.navigation.SELECTED_ACHIEVEMENT_ROUTE_WITH_ARGUMENTS
import com.example.sportapplication.ui.activity.main.MainScreen
import com.example.sportapplication.ui.introduction.navigation.INTRODUCTION_ROUTE
import com.example.sportapplication.ui.settings.LanguageViewModel
import com.example.sportapplication.ui.settings.LocaleHelper
import com.example.sportapplication.ui.theme.SportApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences // Inject SharedPreferences

    override fun attachBaseContext(newBase: Context) {
        val sharedPreferences = newBase.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("language_key", "en") ?: "en"
        val context = LocaleHelper.wrap(newBase, languageCode)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val languageViewModel: LanguageViewModel = hiltViewModel()
            val selectedLanguage by languageViewModel.selectedLanguage.observeAsState("en")

            // Remember the present value for comparison
            val currentLanguage = remember { mutableStateOf(selectedLanguage) }

            // Sjekk og oppdater språket hvis det er endret
            LaunchedEffect(selectedLanguage) {
                if (currentLanguage.value != selectedLanguage) {
                    currentLanguage.value = selectedLanguage
                    recreate() // Restart activity to reflect change
                }
            }

            SportApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val isBottomBarVisible = remember { mutableStateOf(true) }
                    val navHostController = rememberNavController()

                    navHostController.addOnDestinationChangedListener { _, destination, _ ->
                        isBottomBarVisible.value =
                            destination.route != INTRODUCTION_ROUTE &&
                                    destination.route != SELECTED_ACHIEVEMENT_ROUTE_WITH_ARGUMENTS
                    }

                    MainScreen(
                        navController = navHostController,
                        showBottomBar = isBottomBarVisible.value,
                        sharedPreferences = sharedPreferences // Pass SharedPreferences to MainScreen
                    )
                }
            }
        }
    }
}
