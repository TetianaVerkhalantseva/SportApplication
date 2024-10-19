package com.example.sportapplication.ui.activity.main

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sportapplication.R
import com.example.sportapplication.ui.activity.navigation.AppNavHost
import com.example.sportapplication.ui.settings.LanguageViewModel
import com.example.sportapplication.ui.settings.updateLocale
import com.example.sportapplication.ui.theme.SportApplicationTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    showBottomBar: Boolean
) {
    var showMenu by remember { mutableStateOf(false) }
    var showLanguageMenu by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val languageViewModel: LanguageViewModel = hiltViewModel()

    // State to track the dark mode toggle
    var isDarkTheme by remember { mutableStateOf(false) }

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
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                        ) {
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

                            // Submenu for selecting the language
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
                                                languageViewModel.setLanguage(code)
                                                (navController.context.applicationContext as? Application)?.updateLocale(code)
                                                showLanguageMenu = false
                                                showMenu = false
                                                (navController.context as ComponentActivity).recreate()
                                            }
                                        )
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

                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.settings), style = MaterialTheme.typography.bodyLarge) },
                                onClick = {
                                    scope.launch {
                                        // Navigate to settings
                                        showMenu = false
                                    }
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
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
                }
            }
        )
    }
}
