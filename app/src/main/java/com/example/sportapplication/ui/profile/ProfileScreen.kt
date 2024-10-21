package com.example.sportapplication.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sportapplication.R
import com.example.sportapplication.ui.theme.PrimaryButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Profile screen where a user can view/change username and see player statistics.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, viewModel: ProfileViewModel = hiltViewModel()) {
    val nickname by viewModel.nickname.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    // State variables for managing nickname input, editing state, and status message
    var nicknameTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var isEdited by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<Int?>(null) }

    // Dummy statistics for demonstration
    val completedQuests = 12
    val distanceTraveled = "120 km"
    val totalAchievements = 5
    val inventoryItems = 30
    val completedTasks = 50
    val poisVisited = 15

    // Update only if the user has not started editing
    LaunchedEffect(nickname) {
        if (!isEdited && nickname.isNotBlank()) {
            nicknameTextFieldValue = TextFieldValue("")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.profile), style = MaterialTheme.typography.titleLarge)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Display current nickname as header
                Text(
                    text = "${stringResource(R.string.current_nickname)}: $nickname",
                    style = MaterialTheme.typography.titleMedium
                )

                // Text field for entering new nickname
                TextField(
                    value = nicknameTextFieldValue,
                    onValueChange = { newNickname ->
                        nicknameTextFieldValue = newNickname
                        isEdited = true
                    },
                    placeholder = {
                        val placeholderText = stringResource(R.string.enter_new_nickname)
                        Text(text = placeholderText)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                PrimaryButton(
                    text = stringResource(R.string.save),
                    onClick = {
                        coroutineScope.launch {
                            val isSuccessful = viewModel.updateNickname(nicknameTextFieldValue.text)
                            statusMessage = if (isSuccessful) {
                                R.string.nickname_saved_successfully
                            } else {
                                R.string.nickname_save_failed
                            }

                            // Launch a coroutine to remove the message after 3 seconds
                            coroutineScope.launch {
                                delay(3000)
                                statusMessage = null
                            }
                            isEdited = false
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                )

                if (statusMessage != null) {
                    Text(
                        text = stringResource(id = statusMessage!!),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Player statistics section
                Text(
                    text = stringResource(R.string.player_statistics),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                StatisticItem(
                    label = stringResource(R.string.completed_quests),
                    value = completedQuests.toString()
                )

                StatisticItem(
                    label = stringResource(R.string.distance_traveled),
                    value = distanceTraveled
                )

                StatisticItem(
                    label = stringResource(R.string.total_achievements),
                    value = totalAchievements.toString()
                )

                StatisticItem(
                    label = stringResource(R.string.inventory_items),
                    value = inventoryItems.toString()
                )

                StatisticItem(
                    label = stringResource(R.string.completed_tasks),
                    value = completedTasks.toString()
                )

                StatisticItem(
                    label = stringResource(R.string.pois_visited),
                    value = poisVisited.toString()
                )
            }
        }
    )
}

// Display individual statistic items with a label and value
@Composable
fun StatisticItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )
    }
}
