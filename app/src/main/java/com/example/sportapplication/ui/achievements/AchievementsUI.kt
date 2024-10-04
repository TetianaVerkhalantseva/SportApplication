package com.example.sportapplication.ui.achievements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import com.example.sportapplication.R
import com.example.sportapplication.database.model.Achievement
import kotlin.math.ceil

private const val ACHIEVEMENTS_IN_A_ROW = 3.0

@Composable
fun TotalWorkoutsPanel(
    achievements: List<Achievement>,
    onAchievementClicked : (Achievement) -> Unit
) {
    Column {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.total_workouts),
            fontSize = 18.sp
        )
        WorkoutsGrid(
            achievements = achievements,
            onItemClick = {
                onAchievementClicked(it)
            }
        )
    }
}


@Composable
fun WorkoutsInWeekPanel(
    achievements: List<Achievement>,
    onAchievementClicked : (Achievement) -> Unit
) {
    Column {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.workouts_in_a_week),
            fontSize = 18.sp
        )
        WorkoutsGrid(
            achievements = achievements,
            onItemClick = {
                onAchievementClicked(it)
            }
        )
    }
}

@Composable
fun WorkoutsGrid(
    achievements: List<Achievement>,
    onItemClick: (Achievement) -> Unit
) {
    run breaking@{
        var index = 0
        repeat(ceil(achievements.size / ACHIEVEMENTS_IN_A_ROW).toInt()) {
            Row {
                for (i in 1..ACHIEVEMENTS_IN_A_ROW.toInt()) {
                    if (index < achievements.size) {
                        val achievement = achievements.getOrNull(index)
                        achievement?.let { item ->
                            WorkoutItem(
                                modifier = Modifier
                                    .weight(1F),
                                image = item.icon,
                                title = stringResource(id = item.title),
                                onItemClick = { onItemClick(item) }
                            )
                            index++
                        }
                    } else Spacer(modifier = Modifier.weight(1F))
                }
            }
        }
    }
}

@Composable
fun WorkoutItem(
    modifier: Modifier,
    image: Int,
    title: String,
    onItemClick: () -> Unit
) {
    Column(
        modifier = modifier.clickable {
            onItemClick()
        }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = image),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Text(
            text = title
        )
    }
}