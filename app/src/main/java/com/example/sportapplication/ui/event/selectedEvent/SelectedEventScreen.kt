package com.example.sportapplication.ui.event.selectedEvent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.R
import com.example.sportapplication.database.model.Item
import com.example.sportapplication.repository.model.EventWithQuestsUI
import com.example.sportapplication.ui.inventory.ItemUI
import com.example.sportapplication.ui.map.CountdownTimer

@Composable
fun SelectedEventRoute(
    eventId: Long?,
    navigateToMapScreen: () -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel: SelectedEventViewModel = hiltViewModel()
    val event by viewModel.event.collectAsState()
    val item = viewModel.item

    LaunchedEffect(key1 = eventId) {
        viewModel.setEventId(eventId)
    }

    SelectedEventScreen(
        event = event,
        navigateToMapScreen = navigateToMapScreen,
        onBackClick = onBackClick,
        item = item
    )
}

@Composable
fun SelectedEventScreen(
    event: EventWithQuestsUI?,
    navigateToMapScreen: () -> Unit,
    onBackClick: () -> Unit,
    item: Item?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Start)
                .size(48.dp)
                .clickable {
                    onBackClick()
                }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        event?.let { quest ->
            Column {
                // Display the event icon
                Image(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    painter = painterResource(id = event.icon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Display the name and description of the event
                Text(
                    text = stringResource(id = event.name),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (event.isCompleted) {
                    Text(
                        text = stringResource(R.string.completed),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                } else {
                    CountdownTimer(
                        modifier = Modifier,
                        tillTimeInMilliseconds = event.startTime.plus(event.duration)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.width(280.dp),
                    text = stringResource(id = quest.description ?: R.string.default_description),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Reward: ${event.getTotalReward()} XP",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if(item != null){
                    Text("Reward Item")
                    ItemUI(item, Modifier.width(200.dp))

                }

                Spacer(modifier = Modifier.height(10.dp))

                // Display locations and tasks
                event.quests.forEach { quest ->
                    quest.locationWithTasks.forEach { locationWithTasks ->
                        Row {
                            Icon(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                painter = painterResource(id = locationWithTasks.interestingLocation.icon),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                modifier = Modifier.align(Alignment.CenterVertically),
                                text = stringResource(id = locationWithTasks.interestingLocation.name),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Tasks:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiary
                        )

                        locationWithTasks.tasks.forEachIndexed { index, task ->
                            Row {
                                Text(
                                    text = index.plus(1).toString().plus("."),
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(id = task.description),
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier
                .height(8.dp)
                .weight(1F))

            // Button go to map
            OutlinedButton(
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.tertiary),
                shape = RoundedCornerShape(20.dp),
                onClick = { navigateToMapScreen() },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.go_to_map),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}