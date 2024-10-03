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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sportapplication.R
import com.example.sportapplication.ui.activity.main.BottomNavBar
import com.example.sportapplication.ui.activity.navigation.AppNavHost
import com.example.sportapplication.ui.theme.SportApplicationTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    showBottomBar: Boolean
) {
    var showMenu by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // State to track the dark mode toggle
    var isDarkTheme by remember { mutableStateOf(false) }

    SportApplicationTheme(darkTheme = isDarkTheme) { // Pass the theme state here
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.questabout), style = MaterialTheme.typography.titleLarge) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,  // TopAppBar background color
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer  // TopAppBar text color
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
                            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)  // Same color as TopAppBar
                        ) {
                            DropdownMenuItem(
                                text = { Text("Change Language", style = MaterialTheme.typography.bodyLarge) },
                                onClick = {
                                    scope.launch {
                                        // Language change logic here
                                        showMenu = false
                                    }
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Change Theme", style = MaterialTheme.typography.bodyLarge) },
                                onClick = {
                                    // Toggle dark theme
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
                                text = { Text("Settings", style = MaterialTheme.typography.bodyLarge) },
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
