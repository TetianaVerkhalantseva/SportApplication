package com.example.sportapplication.ui.quest

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sportapplication.R
import com.example.sportapplication.database.model.Quest


@Composable
fun LazyQuestsColumn(quests: List<Quest>, onQuestClick: (Quest) -> Unit) {
    LazyColumn {
        items(quests) { quest ->
            LazyQuestItem(
                modifier = Modifier
                    .padding(10.dp),
                quest = quest,
                onClick = { onQuestClick(quest) }
            )
        }
    }
}

@Composable
fun LazyQuestItem(
    modifier : Modifier = Modifier,
    quest: Quest,
    onClick: () -> Unit
) {

    val amountOfTasks = quest.locationWithTasks.tasks.size

    Row(
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = modifier,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .sizeIn(minHeight = 72.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp))

                ) {
                    Image(
                        painter = painterResource(quest.icon),
                        contentDescription = null,
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.FillWidth
                    )
                }
                Spacer(Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = quest.name),
                        style = MaterialTheme.typography.displaySmall
                    )

                    Text(
                        text = stringResource(if (quest.isCompleted) R.string.completed else R.string.active),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = stringResource(
                            R.string.amount_of_tasks_event_placeholder,
                            amountOfTasks
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = stringResource(
                            R.string.event_reward_placeholder,
                            quest.reward.experience
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
