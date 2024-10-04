package com.example.sportapplication.ui.sensor

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SensorScreenRoute() {
    val viewModel: SensorViewModel = hiltViewModel()
    val rotation = viewModel.rotation
    val rotationCurrent = viewModel.rotationCurrent
    val acceleration = viewModel.acceleration
    val linearAcceleration = viewModel.linearAcceleration

    SensorScreen(rotation, rotationCurrent, acceleration, linearAcceleration)
}

@Composable
fun SensorScreen(rotation: FloatArray, rotationCurrent: FloatArray, acceleration: FloatArray, linearAcceleration: SnapshotStateList<Float>){

    Column {
        Text(text="Sensor screen")
        Text(text="${rotation[0]}, ${rotation[1]}, ${rotation[2]}")
        Text(text="${rotationCurrent[0]}, ${rotationCurrent[5]}, ${rotationCurrent[8]}")
        Text(text="${acceleration[0]}, ${acceleration[1]}, ${acceleration[2]}" )
        Text(text="${linearAcceleration[0]}, ${linearAcceleration[1]}, ${linearAcceleration[2]}" )

    }
}