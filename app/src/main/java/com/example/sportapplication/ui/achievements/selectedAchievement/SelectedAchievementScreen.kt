package com.example.sportapplication.ui.achievements.selectedAchievement

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.R
import com.example.sportapplication.database.model.Achievement

@Composable
fun SelectedAchievementRoute(
    achievementId: Long?,
    onBackClick: () -> Unit
) {
    val viewModel: SelectedAchievementViewModel = hiltViewModel()
    val achievement by viewModel.selectedAchievement.collectAsState()

    LaunchedEffect(key1 = achievementId) {
        viewModel.getAchievementById(achievementId)
    }

    SelectedAchievementScreen(
        achievement = achievement,
        onBackClick = onBackClick
    )
}

@Composable
fun SelectedAchievementScreen(
    achievement: Achievement?,
    onBackClick: () -> Unit
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
                imageVector = Icons.Filled.Close, // Updated to Material 3 Icons
                contentDescription = null,
                tint = Color.Gray
            )
        }
        achievement?.let {
            Icon(
                modifier = Modifier
                    .size(300.dp),
                imageVector = ImageVector.vectorResource(id = achievement.icon),
                contentDescription = null,
                tint = Color.Unspecified // @TODO gray when not achieved, color when achieved
            )

            //@TODO Add date when achieved
            Text(
                text = stringResource(id = achievement.title),
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = achievement.notAchievedDescription), // @TODO different description for achieved/not achieved
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.weight(1F))

            OutlinedButton(
                border = BorderStroke(1.dp, color = Color.Red),
                shape = RoundedCornerShape(20.dp),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White // Updated to use correct colors
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.share_badge),
                    fontSize = 16.sp
                )
            }
        }
    }
}
