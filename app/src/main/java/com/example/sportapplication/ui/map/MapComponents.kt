package com.example.sportapplication.ui.map

import android.util.Log
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
import com.example.sportapplication.database.model.Event
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.database.model.Task
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun EventDialog(
    event: Event,
    onStartEventClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = {
        Log.e("DISMISS TIME", "DISMISS TIME")
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
                        painter = painterResource(id = event.icon),
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
                    text = stringResource(id = event.name),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                CountdownTimer(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    tillTimeInMilliseconds = event.startTime + event.duration
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

@Composable
fun CountdownTimer(
    modifier: Modifier,
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
        "Expired"
    }
    
    Column (
        modifier = modifier
    ) {
        Text(
            text = timeText
        )
    }
}

@Composable
fun QuestDialog(
    quest: Quest,
    currentTask: Task,
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
                        text = stringResource(id = task.description),
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
