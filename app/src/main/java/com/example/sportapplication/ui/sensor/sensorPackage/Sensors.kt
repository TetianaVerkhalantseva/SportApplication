package com.example.sportapplication.ui.sensor.sensorPackage

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

//class LightSensor(context: Context): AndroidSensor(context = context, sensorFeature = PackageManager.FEATURE_SENSOR_LIGHT, sensorType= Sensor.TYPE_LIGHT)

class GyroscopeSensor(context: Context): AndroidSensor(context = context, sensorFeature = PackageManager.FEATURE_SENSOR_GYROSCOPE, sensorType= Sensor.TYPE_GYROSCOPE)

class AccelerometerSensor(context: Context): AndroidSensor(context = context, sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER, sensorType= Sensor.TYPE_ACCELEROMETER)

class MagneticSensor(context: Context): AndroidSensor(context = context, sensorFeature = PackageManager.FEATURE_SENSOR_COMPASS, sensorType= Sensor.TYPE_MAGNETIC_FIELD)
