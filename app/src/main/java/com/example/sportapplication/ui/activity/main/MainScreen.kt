package com.example.sportapplication.ui.activity.main

import android.app.Application
import android.content.SharedPreferences
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sportapplication.R
import com.example.sportapplication.ui.activity.navigation.AppNavHost
import com.example.sportapplication.ui.profile.navigation.navigateToProfile
import com.example.sportapplication.ui.settings.LanguageViewModel
import com.example.sportapplication.ui.settings.UnitViewModel
import com.example.sportapplication.ui.settings.updateLocale
import com.example.sportapplication.ui.theme.SportApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    showBottomBar: Boolean,
    sharedPreferences: SharedPreferences
) {
    // State variables for managing the visibility of various menus and splash screen
    var showMenu by remember { mutableStateOf(false) }
    var showLanguageMenu by remember { mutableStateOf(false) }
    var showSettingsMenu by remember { mutableStateOf(false) }
    var showUnitMenu by remember { mutableStateOf(false) }
    var showSplash by remember { mutableStateOf(false) } // For splash screen

    // Coroutine scope for launching coroutine jobs within the composable
    val scope = rememberCoroutineScope()

    // ViewModels for managing language and unit settings
    val languageViewModel: LanguageViewModel = hiltViewModel()
    val unitViewModel: UnitViewModel = hiltViewModel()

    var isDarkTheme by remember { mutableStateOf(false) }

    // Check if the app is opened for the first time
    LaunchedEffect(Unit) {
        delay(3500)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)
        if (isFirstLaunch) {
            showMenu = true // Expand the menu for the first time
            showSplash = true // Show the splash screen
            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
        }
    }
    // Applying the app's theme, either dark or light, based on the state
    SportApplicationTheme(darkTheme = isDarkTheme) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.questabout),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    actions = {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        // Dropdown menu for different options
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                        ) {

                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.profile), style = MaterialTheme.typography.bodyLarge) },
                                onClick = {
                                    showMenu = false
                                    navController.navigateToProfile() // Navigates to profile screen
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            )

                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.change_language), style = MaterialTheme.typography.bodyLarge) },
                                onClick = {
                                    showLanguageMenu = !showLanguageMenu
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            )

                            if (showLanguageMenu) {
                                DropdownMenu(
                                    expanded = showLanguageMenu,
                                    onDismissRequest = { showLanguageMenu = false },
                                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                                ) {
                                    val languages = mapOf(
                                        "en" to "English",
                                        "no" to "Norsk",
                                        "de" to "Deutsch",
                                        "fr" to "Français",
                                        "es" to "Español"
                                    )

                                    languages.forEach { (code, name) ->
                                        DropdownMenuItem(
                                            text = { Text(name, style = MaterialTheme.typography.bodyLarge) },
                                            onClick = {
                                                scope.launch {
                                                    languageViewModel.setLanguage(code)
                                                    (navController.context.applicationContext as? Application)?.updateLocale(code)
                                                    showLanguageMenu = false
                                                    showMenu = false
                                                    (navController.context as ComponentActivity).recreate()
                                                }
                                            }
                                        )
                                    }
                                }
                            }

                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.settings), style = MaterialTheme.typography.bodyLarge) },
                                onClick = {
                                    showSettingsMenu = !showSettingsMenu
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            )

                            if (showSettingsMenu) {
                                DropdownMenu(
                                    expanded = showSettingsMenu,
                                    onDismissRequest = { showSettingsMenu = false },
                                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                                ) {
                                    DropdownMenuItem(
                                        text = { Text(stringResource(R.string.change_unit), style = MaterialTheme.typography.bodyLarge) },
                                        onClick = {
                                            showUnitMenu = !showUnitMenu
                                        }
                                    )

                                    if (showUnitMenu) {
                                        DropdownMenu(
                                            expanded = showUnitMenu,
                                            onDismissRequest = { showUnitMenu = false },
                                            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                                            offset = DpOffset(x = 0.dp, y = 200.dp)
                                        ) {
                                            val units = mapOf(
                                                "metric" to stringResource(R.string.metric_system),
                                                "imperial" to stringResource(R.string.imperial_system)
                                            )

                                            units.forEach { (unitType, displayName) ->
                                                DropdownMenuItem(
                                                    text = { Text(displayName, style = MaterialTheme.typography.bodyLarge) },
                                                    onClick = {
                                                        scope.launch {
                                                            unitViewModel.setUnitSystem(unitType)
                                                            showUnitMenu = false
                                                            showSettingsMenu = false
                                                            showMenu = false
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.change_theme), style = MaterialTheme.typography.bodyLarge) },
                                onClick = {
                                    isDarkTheme = !isDarkTheme
                                    showMenu = false
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.theme_light_dark),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            )
                        }
                    }
                )
            },
            bottomBar = {
                if (showBottomBar) BottomNavBar(navController = navController)
            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    AppNavHost(navHostController = navController)

                    // Show splash screen if it's the first launch
                    if (showSplash) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White.copy(alpha = 0.8f))
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = stringResource(R.string.change_your_settings_here),
                                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                                    modifier = Modifier.padding(16.dp)
                                )
                                Button(onClick = { showSplash = false }) {
                                    Text(stringResource(R.string.got_it))
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}
