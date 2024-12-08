package com.example.sportapplication.ui.map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.sportapplication.R
import com.example.sportapplication.database.model.EventQuest
import com.example.sportapplication.database.model.EventResponseBody
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.database.model.Task
import com.example.sportapplication.repository.model.Event
import com.example.sportapplication.repository.model.QuestInProgress
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun EventDialog(
    eventResponseBody: Event,
    onStartEventClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = {
    }) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = eventResponseBody.icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = stringResource(id = R.string.event)
                        )
                    Icon(
                        modifier = Modifier.clickable {
                            onDismiss()
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }

                Text(
                    text = stringResource(id = eventResponseBody.name),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                if (eventResponseBody.isCompleted) {
                    Text(
                        text = stringResource(id = R.string.event_is_completed),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else {
                    CountdownTimer(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        tillTimeInMilliseconds = eventResponseBody.startTime + eventResponseBody.duration
                    )

                    Button(
                        onClick = {
                            onStartEventClick()
                        },
                    ) {
                        Text(text = stringResource(id = R.string.start_event_quests))
                    }
                }
            }
        }
    }
}

@Composable
fun CountdownTimer(
    modifier: Modifier,
    color: Color = MaterialTheme.colorScheme.error,
    tillTimeInMilliseconds: Long
) {
    val remainingTime = remember {
        mutableStateOf(
            tillTimeInMilliseconds / 1000 -  Calendar.getInstance().timeInMillis / 1000
        )
    }
    
    LaunchedEffect(key1 = remainingTime) {
        while (remainingTime.value > 0) {
            delay(1000L)
            remainingTime.value = remainingTime.value - 1
        }
    }

    val timeText = if (remainingTime.value > 0) {
        val hours = (remainingTime.value / 3600).toInt()
        val minutes = (remainingTime.value % 3600 / 60).toInt()
        val seconds = (remainingTime.value % 60).toInt()
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        stringResource(id = R.string.event_is_expired)
    }
    
    Column (
        modifier = modifier
    ) {
        Text(
            text = timeText,
            color = color
        )
    }
}

@Composable
fun EventQuestDialog(
    eventQuest: EventQuest,
    currentTask: Task,
    currentEventTimeOutMillis: Long?,
    onTaskCompleted: () -> Unit,
    onDismiss: () -> Unit
) {

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = eventQuest.icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = stringResource(id = eventQuest.title),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        modifier = Modifier.clickable { onDismiss() },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }

                currentTask?.let { task ->
                    Text(
                        text = stringResource(id = task.description),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    CountdownTimer(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        tillTimeInMilliseconds = currentEventTimeOutMillis ?: 0
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            onTaskCompleted()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.task_completed))
                    }
                }
            }
        }
    }
}

@Composable
fun NotAvailableQuestLineDialog(
    notAvailableQuestLine: NotAvailableQuestLine,
    onConfirm: () -> Unit
) {

    Dialog(onDismissRequest = onConfirm) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.go_to_quest_location_placeholder, stringResource(
                            id = notAvailableQuestLine.locationName)),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Icon(
                        modifier = Modifier.clickable { onConfirm() },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }
                notAvailableQuestLine.distance?.let {
                    Text(
                        text = stringResource(
                            id = R.string.distance_to_next_quest_location,
                            it.toInt()
                        ),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            onConfirm()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
            }
        }
    }
}


@Composable
fun ContinueCompletingEventDialog(
    eventResponseBody: EventResponseBody,
    onContinueClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = eventResponseBody.icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = stringResource(id = R.string.event)
                    )
                    Icon(
                        modifier = Modifier.clickable {
                            onDismiss()
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }

                Text(
                    text = stringResource(id = eventResponseBody.name),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                CountdownTimer(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    tillTimeInMilliseconds = eventResponseBody.startTime + eventResponseBody.duration
                )

                Button(
                    onClick = {
                        onContinueClick()
                    },
                ) {
                    Text(text = stringResource(id = R.string.continue_events_quest))
                }
            }
        }
    }
}
@Composable
fun CompletedEventDialog(
    completedEvent: CompletedEvent,
    onConfirmClick: () -> Unit,
    itemEffectOnExperience: Long
) {
    Dialog(onDismissRequest = onConfirmClick) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.event)
                    )
                    Icon(
                        modifier = Modifier.clickable {
                            onConfirmClick()
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }

                Text(
                    text = stringResource(id = R.string.completed_event_reward_placeholder, (completedEvent.reward + itemEffectOnExperience)),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Button(
                    onClick = {
                        onConfirmClick()
                    },
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        }
    }
}


@Composable
fun StartQuestDialog(
    quest: Quest,
    onStartClick: () -> Unit,
    onDismiss: () -> Unit
) {

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = quest.icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = stringResource(id = quest.title),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        modifier = Modifier.clickable { onDismiss() },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            onStartClick()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.start_completing_quest))
                    }
                }
            }
        }
    }
}


@Composable
fun QuestDialog(
    quest: QuestInProgress,
    onTaskCompleted: () -> Unit,
    onDismiss: () -> Unit
) {

    val currentTask = quest.locationWithTasks.tasks.find { it.isInProgress }?.task

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = quest.icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = stringResource(id = quest.title),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        modifier = Modifier.clickable { onDismiss() },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }

                currentTask?.let { task ->
                    Text(
                        text = stringResource(id = task.description ),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            onTaskCompleted()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.task_completed))
                    }
                }
            }
        }
    }
}


@Composable
fun CompletedQuestDialog(
    quest: Quest,
    onConfirmClick: () -> Unit,
    itemEffectOnExperience: Long
) {
    Dialog(onDismissRequest = onConfirmClick) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.quest)
                    )
                    Icon(
                        modifier = Modifier.clickable {
                            onConfirmClick()
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                }

                Text(
                    text = stringResource(id = R.string.completed_event_reward_placeholder, (quest.reward.experience + itemEffectOnExperience)),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Button(
                    onClick = {
                        onConfirmClick()
                    },
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        }
    }
}
