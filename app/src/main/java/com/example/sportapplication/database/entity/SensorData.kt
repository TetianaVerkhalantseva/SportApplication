package com.example.sportapplication.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensorData")
data class SensorData(@PrimaryKey val timestamp: Long, @ColumnInfo(name = "gyroscope") val gyroscopeReading: FloatArray?, @ColumnInfo(name = "accelerometer") val acceleromoeterReading: FloatArray?, @ColumnInfo(name = "magnetic_field") val magneticReading: FloatArray? ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SensorData

        if (timestamp != other.timestamp) return false
        if (gyroscopeReading != null) {
            if (other.gyroscopeReading == null) return false
            if (!gyroscopeReading.contentEquals(other.gyroscopeReading)) return false
        } else if (other.gyroscopeReading != null) return false
        if (acceleromoeterReading != null) {
            if (other.acceleromoeterReading == null) return false
            if (!acceleromoeterReading.contentEquals(other.acceleromoeterReading)) return false
        } else if (other.acceleromoeterReading != null) return false
        if (magneticReading != null) {
            if (other.magneticReading == null) return false
            if (!magneticReading.contentEquals(other.magneticReading)) return false
        } else if (other.magneticReading != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = timestamp.hashCode()
        result = 31 * result + (gyroscopeReading?.contentHashCode() ?: 0)
        result = 31 * result + (acceleromoeterReading?.contentHashCode() ?: 0)
        result = 31 * result + (magneticReading?.contentHashCode() ?: 0)
        return result
    }
}
