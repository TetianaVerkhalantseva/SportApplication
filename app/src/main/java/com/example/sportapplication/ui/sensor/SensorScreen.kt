package com.example.sportapplication.ui.sensor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.database.entity.SensorData
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun SensorScreenRoute() {
    val viewModel: SensorViewModel = hiltViewModel()
    val rotation = viewModel.rotation
    val rotationCurrent = viewModel.rotationCurrent
    val acceleration = viewModel.acceleration
    val linearAcceleration = viewModel.linearAcceleration
    val orientation = viewModel.orientation
    val numberOfRecordings = viewModel.numberOfRecordings
    val rowsOfData = viewModel.rowsOfData
    val currentAverageAcceleration = viewModel.currentAverageAcceleration
    val currentMagnitudeAcceleration = viewModel.magnitudeOfAcceleration


    SensorScreen(
        rotation,
        rotationCurrent,
        orientation,
        acceleration,
        linearAcceleration,
        numberOfRecordings,
        rowsOfData,
        currentAverageAcceleration,
        currentMagnitudeAcceleration
    )
}

@Composable
fun SensorScreen(
    rotation: FloatArray,
    rotationCurrent: FloatArray,
    orientation: FloatArray,
    acceleration: FloatArray,
    linearAcceleration: SnapshotStateList<Float>,
    numberOfRecordings: Int,
    rowsOfData: List<SensorData>?,
    currentAverageAcceleration: FloatArray,
    currentMagnitudeAcceleration: Float
) {
    Column {
        Text(text = "Sensor screen")
        Text(text = "Raw gyroscope: ${rotation[0]}, ${rotation[1]}, ${rotation[2]}")
        Text(text = "Filtered gyroscope: ${rotationCurrent[0]}, ${rotationCurrent[5]}, ${rotationCurrent[8]}")
        Text(text = "Raw orientation: ${orientation[0]}, ${orientation[1]}, ${orientation[2]}")
        Text(text = "Raw acceleration: ${acceleration[0]}, ${acceleration[1]}, ${acceleration[2]}")
        Text(text = "Filtered acceleration: ${linearAcceleration[0]}, ${linearAcceleration[1]}, ${linearAcceleration[2]}")
        Text(text = "Number of seonsor recordings stored: $numberOfRecordings")

        Text(text = "Average Accel: ${currentAverageAcceleration[0]}, ${currentAverageAcceleration[0]}, ${currentAverageAcceleration[0]}")
        Text(text = "Magnitude Accel: $currentMagnitudeAcceleration")

        if (rowsOfData != null) LazyColumn(Modifier.padding(top = 16.dp)) {
            items(rowsOfData) { data ->
                Column(Modifier.padding(bottom = 8.dp)) {
                    Text(text = "timestamp: ${data.timestamp}")
                    Text(text = "gyroscope: [${data.gyroscopeX}, ${data.gyroscopeY}, ${data.gyroscopeZ}]")
                    Text(text = "acceleration: [${data.accelerometerX}, ${data.accelerometerY}, ${data.accelerometerZ}]")
                    Text(text = "magnetic: [${data.magneticX}, ${data.magneticY}, ${data.magneticZ}]")
                }

            }
        }

    }
}