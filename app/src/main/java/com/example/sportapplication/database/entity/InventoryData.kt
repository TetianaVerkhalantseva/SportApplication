package com.example.sportapplication.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensorData")
data class InventoryData(
    @PrimaryKey val timestamp: Long,
    @ColumnInfo(name = "gyroscope_x") val gyroscopeX: Float?,
    @ColumnInfo(name = "gyroscope_y") val gyroscopeY: Float?,
    @ColumnInfo(name = "gyroscope_z") val gyroscopeZ: Float?,
    @ColumnInfo(name = "accelerometer_x") val accelerometerX: Float?,
    @ColumnInfo(name = "accelerometer_y") val accelerometerY: Float?,
    @ColumnInfo(name = "accelerometer_z") val accelerometerZ: Float?,
    @ColumnInfo(name = "magnetic_x") val magneticX: Float?,
    @ColumnInfo(name = "magnetic_y") val magneticY: Float?,
    @ColumnInfo(name = "magnetic_z") val magneticZ: Float?
)