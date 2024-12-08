package com.example.sportapplication.ui.achievements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sportapplication.R
import com.example.sportapplication.repository.model.AchievementUI
import kotlin.math.ceil

private const val ACHIEVEMENTS_IN_A_ROW = 3.0

@Composable
fun AchievementPanel(
    achievements: List<AchievementUI>
) {
    AchievementsGrid(
        achievements = achievements
    )
}

@Composable
fun AchievementsGrid(
    achievements: List<AchievementUI>
) {
    run breaking@{
        var index = 0
        repeat(ceil(achievements.size / ACHIEVEMENTS_IN_A_ROW).toInt()) {
            Row {
                for (i in 1..ACHIEVEMENTS_IN_A_ROW.toInt()) {
                    if (index < achievements.size) {
                        val achievement = achievements.getOrNull(index)
                        achievement?.let { item ->
                            AchievementItem(
                                modifier = Modifier
                                    .weight(1F),
                                image =
                                if (item.isCompleted) item.icon
                                else R.drawable.default_achive,
                                title = stringResource(id = item.title)
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
fun AchievementItem(
    modifier: Modifier,
    image: Int,
    title: String
) {
    Column(
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            painter = painterResource(id = image),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}
