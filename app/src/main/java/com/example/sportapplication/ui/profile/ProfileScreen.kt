package com.example.sportapplication.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.sportapplication.utils.getUserStatus
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

    val userExperience by viewModel.userExperience.collectAsState()
    val completedQuests by viewModel.completedQuestsAmount.collectAsState()
    val completedEvents by viewModel.completedEventsAmount.collectAsState()
    val totalAchievements by viewModel.completedAchievementsAmount.collectAsState()
    val inventoryItems = viewModel._totalNumberOfItemsPickedUp

    val poisVisited by viewModel.poiVisitedAmount.collectAsState()

    val statistics = listOf(
        stringResource(R.string.completed_quests) to completedQuests.toString(),
        stringResource(R.string.total_achievements) to totalAchievements.toString(),
        stringResource(R.string.total_items_picked_up) to inventoryItems.toString(),
        stringResource(R.string.completed_events) to completedEvents.toString(),
        stringResource(R.string.pois_visited) to poisVisited.toString(),
        stringResource(R.string.experience) to userExperience.toString(),
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
                    Row {
                        Text(
                            modifier = Modifier.weight(1F),
                            text = stringResource(R.string.profile),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.weight(1F))
                        UserStatus(
                            modifier = Modifier,
                            userExperience = userExperience
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
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
                    Text(
                        text = "${stringResource(R.string.current_nickname)}: $nickname",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    TextField(
                        value = nicknameTextFieldValue,
                        onValueChange = { newNickname ->
                            nicknameTextFieldValue = newNickname
                            isEdited = true
                        },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.enter_new_nickname),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
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
                        }
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

                    nickname?.let {
                        AvatarSelectionSection(
                            selectedAvatar = avatarId,
                            onAvatarSelected = { newAvatarId ->
                                coroutineScope.launch {
                                    viewModel.updateAvatar(newAvatarId)
                                    AvatarHelper.updateAvatar(newAvatarId)
                                }
                            },
                            nickname = it
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.player_statistics),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
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
fun UserStatus(
    modifier: Modifier,
    userExperience: Long
) {
    Text(
        modifier = modifier,
        text = getUserStatus(userExperience),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
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
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
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
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
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
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (selectedAvatar == index) {
                            Text(
                                text = nickname,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        IconButton(
                            onClick = { onAvatarSelected(index) },
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
