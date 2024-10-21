package com.example.sportapplication.ui.sensor

import android.hardware.SensorManager
import android.util.Half.EPSILON
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sportapplication.database.dao.SensorDao
import com.example.sportapplication.database.entity.SensorData
import com.example.sportapplication.ui.sensor.sensorPackage.MultiSensor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val multiSensor: MultiSensor,
    private val sensorDao: SensorDao
) : ViewModel() {

    //DATABASE & DATACOLLECTOR
    private var databaseUpdateTimer = Timer()
    var numberOfRecordings by mutableIntStateOf(0)
    var rowsOfData by mutableStateOf<List<SensorData>?>(null)
    private var initState = true
    private val averageAcceleration = ArrayList<FloatArray>()
    var currentAverageAcceleration by mutableStateOf(floatArrayOf(0f, 0f, 0f))
    private var averagingTimer = Timer()

    //GYROSCOPE
    var rotation by mutableStateOf(floatArrayOf(0f, 0f, 0f))
    private val NS2S = 1.0f / 1000000000f
    private var timestamp: Long = 0L
    var rotationCurrent by mutableStateOf(floatArrayOf(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f))

    //Low-pass filter
    private val alpha: Float = 0.8f
    private var gravity = FloatArray(3)
    var linearAcceleration = mutableStateListOf(0.0f, 0.0f, 0.0f)

    //ACCELEROMETER
    var acceleration by mutableStateOf(floatArrayOf(0f, 0f, 0f))

    //MAGNETIC_FIELD
    var orientation by mutableStateOf(floatArrayOf(0f, 0f, 0f))
    private var magnet = floatArrayOf(0f, 0f, 0f)
    private var accMagOrientation = floatArrayOf(0f, 0f, 0f)
    var rotationMatrix = FloatArray(9)

    fun stopGyroscope() {
        multiSensor.gyroscopeSensor.stopListening()
    }

    fun stopAccelerometer() {
        multiSensor.accelerometerSensor.stopListening()
    }


    init {

        //CLEAR DATABASE FOR TESTING REASONS
        deleteAllPointsInDatabase()
        if (!multiSensor.gyroscopeSensor.sensorActive) {

            //INITIATE SENSOR LISTENERS AND ON CHANGE FUNCTIONALITY
            multiSensor.gyroscopeSensor.startListening()
            multiSensor.gyroscopeSensor.setOnSensorValuesChangedListener { values ->
                this.rotation = values.toFloatArray()

                val currentTimestamp: Long = multiSensor.gyroscopeSensor.timestamp

                // CHECK INIT STATE AND INITIALIZE LATE START VALUES.
                if (initState) {
                    initState = false
                    val initMatrix = getRotationMatrixFromOrientation(accMagOrientation)
                    SensorManager.getOrientation(initMatrix, floatArrayOf(0f, 0f, 0f))
                    rotationMatrix = multiplyMatrices(rotationMatrix, initMatrix)
                    databaseUpdateTimer =
                        fixedRateTimer("databaseUpdateTimer", true, 1000L, 1000L) {
                            updateDatabase()
                        }
                    averagingTimer = fixedRateTimer("averagingTimer", true, 5000L, 5000L) {
                        val numberOfEntries = averageAcceleration.size
                        var summedX = 0f
                        var summedY = 0f
                        var summedZ = 0f

                        averageAcceleration.forEach {
                            summedX += it[0]
                            summedY += it[1]
                            summedZ += it[2]
                        }

                        currentAverageAcceleration = floatArrayOf(
                            summedX / numberOfEntries,
                            summedY / numberOfEntries,
                            summedZ / numberOfEntries
                        )

                        averageAcceleration.clear()
                    }
                }


                val deltaRotationVector = FloatArray(4) { 0f }


                val dT = (currentTimestamp - timestamp) * NS2S

                if (currentTimestamp != 0L) {
                    getRotationFromGyroscope(this.rotation, deltaRotationVector, dT)
                }

                timestamp = currentTimestamp

                val deltaRotationMatrix = FloatArray(9) { 0f }

                SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector)
                rotationCurrent = multiplyMatrices(rotationCurrent, deltaRotationMatrix)

                SensorManager.getOrientation(rotationCurrent, orientation)


            }

            multiSensor.accelerometerSensor.startListening()
            multiSensor.accelerometerSensor.setOnSensorValuesChangedListener { values ->
                this.acceleration = values.toFloatArray()


                //Isolate force of gravity with low-pass filter.
                gravity[0] = alpha * gravity[0] + ((1 - alpha) * values[0])
                gravity[1] = alpha * gravity[1] + ((1 - alpha) * values[1])
                gravity[2] = alpha * gravity[2] + ((1 - alpha) * values[2])


                //Remove gravity contribution with high-pass filter
                linearAcceleration[0] =
                    if (values[0] - gravity[0] > 0.01 || values[0] - gravity[0] < -0.01) values[0] - gravity[0] else 0.0f
                linearAcceleration[1] =
                    if (values[1] - gravity[1] > 0.01 || values[1] - gravity[1] < -0.01) values[1] - gravity[1] else 0.0f
                linearAcceleration[2] =
                    if (values[2] - gravity[2] > 0.01 || values[2] - gravity[2] < -0.01) values[2] - gravity[2] else 0.0f

                averageAcceleration.add(linearAcceleration.toFloatArray())

                calculateAccMagOrientation()

            }

            multiSensor.magneticSensor.startListening()
            multiSensor.magneticSensor.setOnSensorValuesChangedListener { values ->
                this.magnet = values.toFloatArray()
            }
        }
    }


    private fun calculateAccMagOrientation() {
        if (SensorManager.getRotationMatrix(rotationMatrix, null, acceleration, magnet)) {
            SensorManager.getOrientation(rotationMatrix, accMagOrientation)
        }
    }

    private fun deleteAllPointsInDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            sensorDao.deleteAll()
        }
    }

    private fun updateDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            sensorDao.insertRow(
                SensorData(
                    Date().time,
                    rotation[0], rotation[1], rotation[2],
                    acceleration[0], acceleration[1], acceleration[2],
                    magnet[0], magnet[1], magnet[2]
                )
            )

            val rowCount = sensorDao.rowCount()

            if (rowCount > 60) {

                sensorDao.deleteTop(1)
            }

            numberOfRecordings = sensorDao.rowCount()

            rowsOfData = sensorDao.getAll()

        }
        /*
    if(databaseTimestamp == 0L){
        databaseTimestamp = currentTimestamp
    }

    if (currentTimestamp - databaseTimestamp > 1000){
        CoroutineScope(Dispatchers.IO).launch {  sensorDoa.insertRow(
            SensorData(
                currentTimestamp,
                rotation[0], rotation[1], rotation[2],
                acceleration[0], acceleration[1], acceleration[2],
                magnet[0], magnet[1], magnet[2]
            )
        )}
        databaseTimestamp = currentTimestamp
    }*/
    }

}


fun multiplyMatrices(matrixA: FloatArray, matrixB: FloatArray): FloatArray {
    val result = FloatArray(9)

    result[0] = matrixA[0] * matrixB[0] + matrixA[1] * matrixB[3] + matrixA[2] * matrixB[6]
    result[1] = matrixA[0] * matrixB[1] + matrixA[1] * matrixB[4] + matrixA[2] * matrixB[7]
    result[2] = matrixA[0] * matrixB[2] + matrixA[1] * matrixB[5] + matrixA[2] * matrixB[8]

    result[3] = matrixA[3] * matrixB[0] + matrixA[4] * matrixB[3] + matrixA[5] * matrixB[6]
    result[4] = matrixA[3] * matrixB[1] + matrixA[4] * matrixB[4] + matrixA[5] * matrixB[7]
    result[5] = matrixA[3] * matrixB[2] + matrixA[4] * matrixB[5] + matrixA[5] * matrixB[8]

    result[6] = matrixA[6] * matrixB[0] + matrixA[7] * matrixB[3] + matrixA[8] * matrixB[6]
    result[7] = matrixA[6] * matrixB[1] + matrixA[7] * matrixB[4] + matrixA[8] * matrixB[7]
    result[8] = matrixA[6] * matrixB[2] + matrixA[7] * matrixB[5] + matrixA[8] * matrixB[8]

    return result
}


fun getRotationFromGyroscope(values: FloatArray, deltaRotationVector: FloatArray, dT: Float) {

    var axisX: Float = values[0]
    var axisY: Float = values[1]
    var axisZ: Float = values[2]

    val omegaMagnitude: Float = sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ)

    if (omegaMagnitude > EPSILON) {
        axisX /= omegaMagnitude
        axisY /= omegaMagnitude
        axisZ /= omegaMagnitude
    }

    val thetaOverTwo: Float = omegaMagnitude * dT / 2.0f
    val sinThetaOverTwo: Float = sin(thetaOverTwo)
    val cosThetaOverTwo: Float = cos(thetaOverTwo)
    deltaRotationVector[0] = sinThetaOverTwo * axisX
    deltaRotationVector[1] = sinThetaOverTwo * axisY
    deltaRotationVector[2] = sinThetaOverTwo * axisZ
    deltaRotationVector[3] = cosThetaOverTwo

}

fun getRotationMatrixFromOrientation(orientation: FloatArray): FloatArray {
    val xM = FloatArray(9)
    val yM = FloatArray(9)
    val zM = FloatArray(9)

    val sinX = Math.sin(orientation[1].toDouble()).toFloat()
    val cosX = Math.cos(orientation[1].toDouble()).toFloat()
    val sinY = Math.sin(orientation[2].toDouble()).toFloat()
    val cosY = Math.cos(orientation[2].toDouble()).toFloat()
    val sinZ = Math.sin(orientation[0].toDouble()).toFloat()
    val cosZ = Math.cos(orientation[0].toDouble()).toFloat()

    xM[0] = 1.0f
    xM[1] = 0.0f
    xM[2] = 0.0f
    xM[3] = 0.0f
    xM[4] = cosX
    xM[5] = sinX
    xM[6] = 0.0f
    xM[7] = -sinX
    xM[8] = cosX


    yM[0] = cosY
    yM[1] = 0.0f
    yM[2] = sinY
    yM[3] = 0.0f
    yM[4] = 1.0f
    yM[5] = 0.0f
    yM[6] = -sinY
    yM[7] = 0.0f
    yM[8] = cosY

    // rotation about z-axis (azimuth)
    zM[0] = cosZ
    zM[1] = sinZ
    zM[2] = 0.0f
    zM[3] = -sinZ
    zM[4] = cosZ
    zM[5] = 0.0f
    zM[6] = 0.0f
    zM[7] = 0.0f
    zM[8] = 1.0f

    // rotation order is y, x, z (roll, pitch, azimuth)
    var resultMatrix = multiplyMatrices(xM, yM)
    resultMatrix = multiplyMatrices(zM, resultMatrix)
    return resultMatrix
}
