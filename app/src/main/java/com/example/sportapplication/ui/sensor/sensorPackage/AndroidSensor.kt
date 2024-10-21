package com.example.sportapplication.ui.sensor.sensorPackage

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log


abstract class AndroidSensor(
    private val context: Context,
    private val sensorFeature: String,
    sensorType: Int
) : MeasurableSensor(sensorType), SensorEventListener {
    override val doesSensorExist: Boolean
        get() = context.packageManager.hasSystemFeature(sensorFeature)

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    var sensorActive = false
    var timestamp = 0L


    override fun startListening() {
        Log.i("Staring listening to sensor", "initiating")
        if (!doesSensorExist) {
            Log.e("sensor failure", "sensor not found")
            return
        }
        if (!::sensorManager.isInitialized && sensor == null) {
            Log.i("sensor manager setup", "inserting sensor")
            sensorManager = context.getSystemService(SensorManager::class.java) as SensorManager
            sensor = sensorManager.getDefaultSensor(sensorType)
        }
        Log.i("registering sensor", "sensor is being registered ${sensor.toString()}")
        sensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        sensorActive = if (sensor == null) false else true


    }

    override fun stopListening() {
        if (!doesSensorExist || !::sensorManager.isInitialized) {
            return
        }
        sensorManager.unregisterListener(this)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        if (!doesSensorExist) {
            return
        }
        if (event?.sensor?.type == sensorType) {
            onSensorValuesChanged?.invoke(event.values.toList())
            timestamp = event.timestamp
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) = Unit
}