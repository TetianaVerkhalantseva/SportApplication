package com.example.sportapplication.ui.sensor.sensorPackage

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SensorModule {
    @Provides
    @Singleton
    fun provideMultiSensor(app: Application): MultiSensor {
        val multiSensor = MultiSensor(AccelerometerSensor(app),GyroscopeSensor(app), MagneticSensor(app))
        return multiSensor
    }
}

class MultiSensor(val accelerometerSensor: AndroidSensor, val gyroscopeSensor: AndroidSensor, val magneticSensor: MagneticSensor) {

}