package com.example.sportapplication.ui.sensor


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sportapplication.ui.sensor.sensorPackage.AccelerometerSensor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.sportapplication.ui.sensor.sensorPackage.MeasurableSensor
import com.example.sportapplication.ui.sensor.sensorPackage.MultiSensor

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val multiSensor: MultiSensor
): ViewModel() {

        var rotation by mutableStateOf(listOf(0f,0f,0f))
        var accelaration by mutableStateOf(listOf(0f,0f,0f))

        init {
            multiSensor.gyroscopeSensor.startListening()

            multiSensor.gyroscopeSensor.setOnSensorValuesChangedListener { values ->
                this.rotation = values

            }

            multiSensor.accelerometerSensor.startListening()
            multiSensor.accelerometerSensor.setOnSensorValuesChangedListener { values ->
                this.accelaration = values
            }
        }
}