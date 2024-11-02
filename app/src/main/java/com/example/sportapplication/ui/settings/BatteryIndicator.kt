package com.example.sportapplication.ui.settings

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BatteryIndicator(
    batteryViewModel: BatteryViewModel = hiltViewModel()
) {
    // Observing battery level from the view model
    val batteryLevel by batteryViewModel.batteryLevel.observeAsState(100)

    val batteryColor = getBatteryColor(batteryLevel)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                batteryViewModel.decreaseBatteryLevel() // Simulate battery drain on click
            }
    ) {
        // Display battery level percentage text
        Text(
            text = "$batteryLevel%",
            color = batteryColor,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(8.dp))

        // Custom battery icon filled based on battery level
        Canvas(modifier = Modifier.size(width = 24.dp, height = 12.dp)) {
            // Draw the battery frame
            drawRect(
                color = Color.Gray,
                size = size,
                style = Stroke(width = 2f)
            )

            // Draw the fill based on the battery level
            drawRect(
                color = batteryColor,
                size = size.copy(width = size.width * (batteryLevel / 100f))
            )

            // Draw the battery tip (to resemble a real battery icon)
            drawRect(
                color = Color.Gray,
                topLeft = Offset(x = size.width, y = size.height * 0.25f),
                size = size.copy(width = size.width * 0.1f, height = size.height * 0.5f)
            )
        }
    }
}

// Function to calculate the battery color based on the battery level
fun getBatteryColor(batteryLevel: Int): Color {
    val red = when {
        batteryLevel >= 50 -> ((100 - batteryLevel) / 50f * 255).toInt() // Goes from 0 to 255 between 100% and 50%
        else -> 255 // Red is fully on when battery level is below 50%
    }

    val green = when {
        batteryLevel >= 50 -> 255 // Green is fully on when battery level is above 50%
        else -> (batteryLevel / 50f * 255).toInt() // Goes from 255 to 0 between 50% and 0%
    }

    return Color(red, green, 0)
}
