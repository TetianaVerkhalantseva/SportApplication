import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.sportapplication.ui.activity.main.BottomNavBar
import com.example.sportapplication.ui.activity.navigation.AppNavHost
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    showBottomBar: Boolean
) {
    var showMenu by remember { mutableStateOf(false) }  // Variabel for å holde styr på om menyen er åpen
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sport Application") },
                actions = {
                    // Menyikonet (3 prikker) for å åpne innstillingsmenyen
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Settings")
                    }
                    // Menyen som åpnes når brukeren klikker på ikonet
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Change Language") },
                            onClick = {
                                scope.launch {
                                    // Legg til logikk for å endre språk her. Implementeres senere.
                                    showMenu = false
                                }
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Change Theme") },
                            onClick = {
                                scope.launch {
                                    // Legg til logikk for å endre tema her. Implementeres senere.
                                    showMenu = false
                                }
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Settings") },
                            onClick = {
                                scope.launch {
                                    // Naviger til innstillinger eller andre handlinger. Implementeres senere.
                                    showMenu = false
                                }
                            }
                        )
                    }
                }
            )
        },
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