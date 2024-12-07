package com.example.sportapplication.ui.quest.selectedQuest

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportapplication.R
import com.example.sportapplication.database.model.EventQuest
import com.example.sportapplication.database.model.InterestingLocation
import com.example.sportapplication.database.model.LocationWithTasks
import com.example.sportapplication.database.model.Reward
import com.example.sportapplication.database.model.Task

@Composable
fun SelectedQuestRoute(
    onBackClick : () -> Unit,
    navigateToInventoryScreen: () -> Unit
) {

    SelectedQuestScreen (
        onBackClick = onBackClick,
        navigateToInventoryScreen = navigateToInventoryScreen
    )
}


// This is the SelectedQuestScreen. Currently, all data is hardcoded and the logic is not implemented yet.
// The screen is created to display a temporary view for now.
@Composable
fun SelectedQuestScreen(
    onBackClick: () -> Unit,
    navigateToInventoryScreen: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
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
                tint = Color.Gray
            )
        }

        selectedEventQuest?.let { quest ->
            Column {
                // Display the quest image
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .width(200.dp)
                        .height(150.dp),
                    painter = painterResource(id = quest.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Display the name and description of the quest
                Text(
                    text = stringResource(id = quest.title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.width(200.dp),
                    text = stringResource(id = quest.description ?: R.string.default_description),
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Display locations and tasks
                quest.locationWithTasks.forEach { locationWithTasks ->
                    Text(
                        text = stringResource(id = locationWithTasks.interestingLocation.name),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Tasks:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    locationWithTasks.tasks.forEach { task ->
                        Text(
                            text = stringResource(id = task.description),
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Inventory transition card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .clickable {
                        navigateToInventoryScreen()
                    },
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
            ) {
                Row {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.Build,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Inventory")
                    Spacer(modifier = Modifier.weight(1F))
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1F))

            // Quest start button
            OutlinedButton(
                border = BorderStroke(1.dp, color = Color.Red),
                shape = RoundedCornerShape(20.dp),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.Unspecified
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.start_quiz),
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display the reward
            Text(
                text = "Reward: ${quest.reward.experience} XP",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


val selectedEventQuest =
    EventQuest(
        id = 2,
        icon = R.drawable.ic_quest_2,
        image = R.drawable.ic_quest_2_image,
        name = R.string.quest_2_name,
        title = R.string.quest_2_title,
        description = R.string.quest_2_description,
        locationWithTasks = listOf(
            LocationWithTasks(
                interestingLocation = InterestingLocation(
                    id = 1,
                    name = R.string.location_city_park,
                    icon = R.drawable.ic_park,
                    latitude = 69.6495,
                    longitude = 18.9330
                ),
                tasks = listOf(
                    Task(id = 2, description = R.string.task_do_squats_action, isCompleted = false, requiresPhoto = true)
                )
            )
        ),
        isCompleted = false,
        reward = Reward(experience = 800),
        eventId = 2
    )