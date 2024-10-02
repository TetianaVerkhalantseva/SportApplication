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
    fun provideGyroscopeSensor(app: Application): MultiSensor {
        val multiSensor = MultiSensor(AccelerometerSensor(app),GyroscopeSensor(app))
        return multiSensor
    }
}

public class MultiSensor(public final val accelerometerSensor: MeasurableSensor, public final val gyroscopeSensor: MeasurableSensor) {

}