package com.example.sportapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sportapplication.database.entity.SensorData

@Dao
interface SensorDao {
    @Query("SELECT * FROM sensorData")
    suspend fun getAll(): List<SensorData>

    @Query("SELECT :sensorType FROM sensorData")
    suspend fun getSensorData(sensorType: String): List<Float>

    @Query("SELECT * FROM sensorData WHERE timestamp LIKE :timestamp")
    suspend fun getSensorDataAtTime(timestamp: Long): List<SensorData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRow(sensorData: SensorData)

}