package com.example.sportapplication.ui.quest.selectedQuest

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportapplication.R
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.database.model.Reward

@Composable
fun SelectedQuestRoute(
    onBackClick : () -> Unit
) {

    SelectedQuestScreen (
        onBackClick = onBackClick
    )
}

@Composable
fun SelectedQuestScreen(
    onBackClick : () -> Unit
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
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

        selectedQuest?.let { quest ->
            Column {
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .width(200.dp)
                        .height(150.dp),
                    imageVector = ImageVector.vectorResource(id = quest.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = quest.title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier
                        .width(200.dp),
                    text = stringResource(id = quest.description),
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.weight(1F))

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
        }
    }

}

val selectedQuest =
    Quest(
        image = R.drawable.ic_launcher_background,
        title = R.string.quest_screen,
        description = R.string.quest_description1,
        reward = Reward(experience = 500)
    )