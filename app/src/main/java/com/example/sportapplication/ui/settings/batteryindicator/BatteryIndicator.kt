package com.example.sportapplication.ui.settings.batteryindicator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BatteryIndicator(
    batteryViewModel: BatteryViewModel = hiltViewModel(),
    modifier: Modifier
) {
    // Observing battery level from the view model
    val batteryLevel by batteryViewModel.batteryLevel.observeAsState(100)
    val averageBatteryDifference = batteryViewModel.averageBatteryDifference
    val batteryColor = getBatteryColor(batteryLevel)
    val batterTickHeight = 24.dp
    val batteryTickWidth = 16.dp
    var isSecondary by remember { mutableStateOf(false) }



    Column(Modifier
        .clickable
        {
            isSecondary = !isSecondary
        }) {
        AnimatedVisibility(visible = !isSecondary) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
            ) {


                // Display battery level percentage text
                Text(
                    text = "$batteryLevel%",
                    color = batteryColor,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .clip(RoundedCornerShape(45))
                        .background(Color(255, 255, 255, 255))
                        .padding(4.dp)

                )
               // Spacer(modifier = Modifier.width(2.dp))

                // Custom battery icon filled based on battery level

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.scale(0.65F)) {
                    Row(
                        Modifier
                            .border(2.dp, Color.White, shape = RoundedCornerShape(20))
                            .padding(4.dp)
                            ,
                        horizontalArrangement = Arrangement.Absolute.spacedBy(2.dp),

                        ) {
                        Box(
                            Modifier
                                .size(height = batterTickHeight, width = batteryTickWidth)
                                .background(finalTickColor((batteryLevel)))
                        )
                        Box(
                            Modifier
                                .size(height = batterTickHeight, width = batteryTickWidth)
                                .background(if (batteryLevel > 33) Color.White else Color.Transparent)
                        )
                        Box(
                            Modifier
                                .size(height = batterTickHeight, width = batteryTickWidth)
                                .background(if (batteryLevel > 66) Color.White else Color.Transparent)
                        )
                    }
                    Box(
                        Modifier
                            .padding(2.dp)
                            .size(width = 3.dp, height = 8.dp)
                            .background(Color.White)
                    )
                }

            }


        }
        AnimatedVisibility(visible = isSecondary) {
            Column(
                Modifier
                    .clip(RoundedCornerShape(20))
                    .background(getBatteryColor(batteryLevel))
                    .padding(8.dp, 2.dp)
            ) {
                Text(text = "Average usage:", softWrap = true, color = Color.White)
                Text(text = "${averageBatteryDifference.toInt()}% per 10s", color = Color.White)
            }
        }

    }
}

fun finalTickColor(batteryLevel: Int): Color {
    if (batteryLevel in 11..29) return Color.Yellow
    if (batteryLevel < 11) return Color.Red

    return Color.White
}

// Function to calculate the battery color based on the battery level
fun getBatteryColor(batteryLevel: Int): Color {
    val red = when {
        batteryLevel >= 50 -> ((100 - batteryLevel) / 50f * 255).toInt() // Goes from 0 to 255 between 100% and 50%
        else -> 255 // Red is fully on when battery level is below 50%
    }

    val green = when {
        batteryLevel >= 50 -> 200 // Green is fully on when battery level is above 50%
        else -> (batteryLevel / 50f * 200).toInt() // Goes from 255 to 0 between 50% and 0%
    }

    return Color(red, green, 0)
}

