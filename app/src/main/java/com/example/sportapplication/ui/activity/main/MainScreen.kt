package com.example.sportapplication.ui.activity.main

import android.app.Application
import android.content.SharedPreferences
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sportapplication.R
import com.example.sportapplication.ui.about.navigation.navigateToAboutUs
import com.example.sportapplication.ui.activity.navigation.AppNavHost
import com.example.sportapplication.ui.profile.ProfileViewModel
import com.example.sportapplication.ui.profile.navigation.navigateToProfile
import com.example.sportapplication.ui.settings.AvatarHelper
import com.example.sportapplication.ui.settings.batteryindicator.BatteryIndicator
import com.example.sportapplication.ui.settings.batteryindicator.BatteryViewModel
import com.example.sportapplication.ui.settings.LanguageViewModel
import com.example.sportapplication.ui.settings.UnitViewModel
import com.example.sportapplication.ui.settings.updateLocale
import com.example.sportapplication.ui.theme.SportApplicationTheme
import com.example.sportapplication.utils.sensorPackage.SensorModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    navController: NavHostController,
    showBottomBar: Boolean,
    sharedPreferences: SharedPreferences,
    sensors: SensorModel,
    viewModel: ProfileViewModel = hiltViewModel(),
    avatarHelper: AvatarHelper = AvatarHelper
) {
    var showMenu by remember { mutableStateOf(false) }
    var showLanguageMenu by remember { mutableStateOf(false) }
    var showSettingsMenu by remember { mutableStateOf(false) }
    var showUnitMenu by remember { mutableStateOf(false) }
    var showSplash by remember { mutableStateOf(false) }

    val languageViewModel: LanguageViewModel = hiltViewModel()
    val unitViewModel: UnitViewModel = hiltViewModel()
    val batteryViewModel: BatteryViewModel = hiltViewModel()

    val scope = rememberCoroutineScope()
    var isDarkTheme by remember { mutableStateOf(false) }

    val avatarId by AvatarHelper.avatarId.collectAsState()
    val nicknameFromDb by viewModel.nickname.collectAsState()
    val nicknameDynamic by avatarHelper.nickname.collectAsState()
    val nickname = nicknameDynamic ?: nicknameFromDb

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val showAvatar = currentBackStackEntry?.destination?.route == "map_route"

    LaunchedEffect(Unit) {
        delay(3500)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)
        if (isFirstLaunch) {
            showMenu = true
            showSplash = true
            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
        }
    }

    SportApplicationTheme(darkTheme = isDarkTheme) {
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    ) {
                        // Background Image
                        Image(
                            painter = painterResource(id = R.drawable.questabout_dynamic),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer {
                                    clip = true
                                }
                        )
                        // Content in TopAppBar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Questabout text
                            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                                Text(
                                    text = stringResource(R.string.questabout),
                                    style = MaterialTheme.typography.headlineLarge.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        shadow = Shadow(
                                            color = Color(0xFFFFA500), // Orange outline
                                            blurRadius = 8f
                                        )
                                    ),
                                    color = Color.White
                                )
                            }

                            // Battery indicator in the middle of the image
                            Box(contentAlignment = Alignment.BottomCenter) {
                                BatteryIndicator(
                                    batteryViewModel = batteryViewModel,
                                    modifier = Modifier
                                )
                            }

                            // Menu icon and dropdown logic
                            Box(contentAlignment = Alignment.CenterEnd) {
                                IconButton(onClick = { showMenu = !showMenu }) {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = "Menu",
                                        tint = Color.White
                                    )
                                }

                                DropdownMenu(
                                    expanded = showMenu,
                                    onDismissRequest = { showMenu = false },
                                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                                    offset = DpOffset(x = (-50).dp, y = 10.dp)
                                ) {
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                stringResource(R.string.profile),
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        },
                                        onClick = {
                                            showMenu = false
                                            navController.navigateToProfile()
                                        },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Filled.Person,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                        }
                                    )

                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                stringResource(R.string.settings),
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        },
                                        onClick = {
                                            showSettingsMenu = !showSettingsMenu
                                        },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Filled.Settings,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                stringResource(R.string.about_us),
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        },
                                        onClick = {
                                            showMenu = false
                                            navController.navigateToAboutUs()
                                        },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Filled.Info,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                        }
                                    )


                                    if (showSettingsMenu) {
                                        DropdownMenu(
                                            expanded = showSettingsMenu,
                                            onDismissRequest = { showSettingsMenu = false },
                                            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                                            offset = DpOffset(x = (-50).dp, y = 90.dp)
                                        ) {
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        stringResource(R.string.change_theme),
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )
                                                },
                                                onClick = {
                                                    isDarkTheme = !isDarkTheme
                                                    showSettingsMenu = false
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
                                                text = {
                                                    Text(
                                                        stringResource(R.string.change_unit),
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )
                                                },
                                                onClick = {
                                                    showUnitMenu = !showUnitMenu
                                                },
                                                leadingIcon = {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.theme_light_dark),
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                                    )
                                                }
                                            )

                                            if (showUnitMenu) {
                                                DropdownMenu(
                                                    expanded = showUnitMenu,
                                                    onDismissRequest = { showUnitMenu = false },
                                                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                                                    offset = DpOffset( x=0.dp, y = 90.dp)

                                                ) {
                                                    listOf("Metric", "Imperial").forEach { unit ->
                                                        DropdownMenuItem(
                                                            text = {
                                                                Text(
                                                                    text = unit,
                                                                    style = MaterialTheme.typography.bodyLarge
                                                                )
                                                            },
                                                            onClick = {
                                                                scope.launch {
                                                                    unitViewModel.setUnitSystem(unit)
                                                                    showUnitMenu = false
                                                                }
                                                            }
                                                        )
                                                    }
                                                }
                                            }

                                            // Move Change Language into the Settings menu
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        stringResource(R.string.change_language),
                                                        style = MaterialTheme.typography.bodyLarge
                                                    )
                                                },
                                                onClick = {
                                                    showLanguageMenu = !showLanguageMenu
                                                },
                                                leadingIcon = {
                                                    Icon(
                                                        imageVector = Icons.Filled.LocationOn,
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                                    )
                                                }
                                            )

                                            if (showLanguageMenu) {
                                                DropdownMenu(
                                                    expanded = showLanguageMenu,
                                                    onDismissRequest = { showLanguageMenu = false },
                                                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                                                    offset = DpOffset( x=0.dp, y = 90.dp)
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
                                                            text = {
                                                                Text(
                                                                    name,
                                                                    style = MaterialTheme.typography.bodyLarge
                                                                )
                                                            },
                                                            onClick = {
                                                                scope.launch {
                                                                    languageViewModel.setLanguage(code)
                                                                    (navController.context.applicationContext as? Application)?.updateLocale(
                                                                        code
                                                                    )
                                                                    showLanguageMenu = false
                                                                    showSettingsMenu = false
                                                                    showMenu = false
                                                                    (navController.context as ComponentActivity).recreate()
                                                                }
                                                            }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Activity Level Section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(getcolor(sensors), 0.25f, 0.95f))
                            .padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Activity Level",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                        )
                    }
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    BottomNavBar(navController = navController)
                }
            },
            content = { padding ->
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    AppNavHost(navHostController = navController)

                    if (showAvatar) {
                        // Overlay Avatar with Username
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Username over avatar
                                Text(
                                    text = nickname,
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = MaterialTheme.colorScheme.scrim,
                                        fontWeight = Bold,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )

                                // Avatar with circular frame
                                Box(
                                    modifier = Modifier
                                        .size(90.dp)
                                        .background(
                                            Color.White,
                                            shape = CircleShape
                                        )
                                        .padding(5.dp)
                                        .clip(CircleShape)
                                        .clickable { navController.navigateToProfile() }
                                ) {
                                    Image(
                                        painter = painterResource(
                                            id = when (avatarId) {
                                                0 -> R.drawable.avatar_female
                                                1 -> R.drawable.avatar_male
                                                else -> R.drawable.avatar_female
                                            }
                                        ),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(CircleShape)
                                    )
                                }
                            }
                        }
                    }

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

fun getcolor(sensors: SensorModel): Float {
    return (((sensors.currentAverageAcceleration[0] + sensors.currentAverageAcceleration[1] + sensors.currentAverageAcceleration[2]) / 10)).coerceIn(0f, 255f)
}
