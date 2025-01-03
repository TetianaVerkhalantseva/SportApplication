package com.example.sportapplication.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.sportapplication.ui.activity.main.MainScreen
import com.example.sportapplication.ui.profile.ProfileViewModel
import com.example.sportapplication.ui.settings.LanguageViewModel
import com.example.sportapplication.ui.settings.LocaleHelper
import com.example.sportapplication.ui.theme.SportApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import java.io.File
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

        // Configure OSM caching to reduce map data loading by storing tiles locally
        val osmConfig = Configuration.getInstance()
        osmConfig.userAgentValue = packageName
        osmConfig.osmdroidBasePath = File(cacheDir, "osmdroid")
        osmConfig.osmdroidTileCache = File(cacheDir, "osmdroid/tiles")

        setContent {
            val languageViewModel: LanguageViewModel = hiltViewModel()
            val selectedLanguage by languageViewModel.selectedLanguage.observeAsState("en")

            // Remember the present value for comparison
            val currentLanguage = remember { mutableStateOf(selectedLanguage) }

            // Check and update language if changed.
            LaunchedEffect(selectedLanguage) {
                if (currentLanguage.value != selectedLanguage) {
                    currentLanguage.value = selectedLanguage
                    recreate() // Restart activity to reflect change
                }
            }

            SportApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val isBottomBarVisible = remember { mutableStateOf(true) }
                    val viewModel: MainActivityViewModel = hiltViewModel()
                    val profileViewModel: ProfileViewModel = hiltViewModel()
                    val navHostController = rememberNavController()

                    val sensors = viewModel.sensors

                    MainScreen(
                        navController = navHostController,
                        showBottomBar = isBottomBarVisible.value,
                        sharedPreferences = sharedPreferences,
                        sensors = sensors,
                        viewModel = profileViewModel,
                        setBottomBarVisibility = {
                            isBottomBarVisible.value = it
                        }
                    )
                }
            }
        }
    }
}
