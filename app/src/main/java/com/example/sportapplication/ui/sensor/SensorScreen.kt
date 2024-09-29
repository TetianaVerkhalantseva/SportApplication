package com.example.sportapplication.ui.sensor

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.ui.map.MapViewModel

@Composable
fun SensorScreenRoute() {
    val viewModel: MapViewModel = hiltViewModel()

    SensorScreen()
}

@Composable
fun SensorScreen(){
    Column {
        Text(text="Sensor screen")
    }
}