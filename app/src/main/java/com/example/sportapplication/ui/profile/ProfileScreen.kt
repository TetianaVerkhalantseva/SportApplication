package com.example.sportapplication.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sportapplication.R
import com.example.sportapplication.ui.settings.AvatarHelper
import com.example.sportapplication.ui.theme.PrimaryButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, viewModel: ProfileViewModel = hiltViewModel()) {
    val nickname by viewModel.nickname.collectAsState()
    val avatarId by AvatarHelper.avatarId.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var nicknameTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var isEdited by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<Int?>(null) }

    // Dummy statistics
    val statistics = listOf(
        stringResource(R.string.completed_quests) to "12",
        stringResource(R.string.distance_traveled) to "120 km",
        stringResource(R.string.total_achievements) to "5",
        stringResource(R.string.inventory_items) to "30",
        stringResource(R.string.completed_tasks) to "50",
        stringResource(R.string.pois_visited) to "15"
    )

    LaunchedEffect(nickname) {
        if (isEdited && !nickname.isNullOrBlank()) {
            nicknameTextFieldValue = TextFieldValue("")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile),
                        style = MaterialTheme.typography.titleLarge
                    )
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
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    // Nickname Section
                    Text(
                        text = "${stringResource(R.string.current_nickname)}: $nickname",
                        style = MaterialTheme.typography.titleMedium
                    )

                    TextField(
                        value = nicknameTextFieldValue,
                        onValueChange = { newNickname ->
                            nicknameTextFieldValue = newNickname
                            isEdited = true
                        },
                        placeholder = {
                            Text(text = stringResource(R.string.enter_new_nickname))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    PrimaryButton(
                        text = stringResource(R.string.save),
                        onClick = {
                            coroutineScope.launch {
                                val isSuccessful = viewModel.updateNickname(nicknameTextFieldValue.text)
                                AvatarHelper.updateNickname(nicknameTextFieldValue.text)
                                statusMessage = if (isSuccessful) {
                                    R.string.nickname_saved_successfully
                                } else {
                                    R.string.nickname_save_failed
                                }

                                coroutineScope.launch {
                                    delay(3000)
                                    statusMessage = null
                                }
                                isEdited = false
                            }
                        },

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

                    // Avatar Selection Section
                    nickname?.let {
                        AvatarSelectionSection(
                            selectedAvatar = avatarId,
                            onAvatarSelected = { newAvatarId ->
                                coroutineScope.launch {
                                    viewModel.updateAvatar(newAvatarId) // Oppdater i databasen
                                    AvatarHelper.updateAvatar(newAvatarId) // Dynamisk oppdatering via AvatarHelper
                                }
                            },
                            nickname = it // Brukernavn over avataren
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Statistics Section
                    Text(
                        text = stringResource(R.string.player_statistics),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    statistics.forEach { (label, value) ->
                        StatisticItem(label = label, value = value)
                    }
                }
            }
        }
    )
}

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

@Composable
fun AvatarSelectionSection(
    selectedAvatar: Int,
    onAvatarSelected: (Int) -> Unit,
    nickname: String
) {
    val avatarOptions = listOf(
        R.drawable.avatar_female,
        R.drawable.avatar_male
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.choose_avatar),
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            avatarOptions.forEachIndexed { index, avatar ->
                Box(
                    modifier = Modifier
                        .size(90.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Plasser brukernavnet over avataren
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (selectedAvatar == index) {
                            Text(
                                text = nickname,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                onAvatarSelected(index) // Oppdater avatar umiddelbart
                            },
                            modifier = Modifier.size(64.dp)
                        ) {
                            Image(
                                painter = painterResource(id = avatar),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(64.dp)
                                    .border(
                                        width = if (selectedAvatar == index) 2.dp else 0.dp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}
