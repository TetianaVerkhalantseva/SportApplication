package com.example.sportapplication.ui.sensor

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SensorScreenRoute() {
    val viewModel: SensorViewModel = hiltViewModel()
    val rotation = viewModel.rotation
    val acceleration = viewModel.accelaration

    SensorScreen(rotation, acceleration)
}

@Composable
fun SensorScreen(rotation: List<Float>, acceleration: List<Float>){


    Column {
        Text(text="Sensor screen")
        Text(text="${rotation[0]}, ${rotation[1]}, ${rotation[2]}")
        Text(text="${acceleration[0]}, ${acceleration[1]}, ${acceleration[2]}" )
    }
}