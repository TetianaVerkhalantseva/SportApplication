package com.example.sportapplication.ui.sensor


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.sportapplication.sensorPackage.MeasurableSensor

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val gyroscopeSensor: MeasurableSensor): ViewModel() {

        var isMoving by mutableStateOf(false)
        init {
            gyroscopeSensor.startListening()
            gyroscopeSensor.setOnSensorValuesChangedListener { values -> val gyr = values[0]
            isMoving = gyr > 60f;
            }
        }
}