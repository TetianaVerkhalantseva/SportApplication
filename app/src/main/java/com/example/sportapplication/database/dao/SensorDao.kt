package com.example.sportapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sportapplication.database.entity.SensorData

@Dao
interface SensorDao {
    @Query("SELECT * FROM sensorData")
    fun getAll(): List<SensorData>

    @Query("SELECT :sensorType FROM sensorData")
    fun getSensorData(sensorType: String): List<Float>

    @Query("SELECT * FROM sensorData WHERE timestamp LIKE :timestamp")
    fun getSensorDataAtTime(timestamp: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRow(sensorData: SensorData)

}