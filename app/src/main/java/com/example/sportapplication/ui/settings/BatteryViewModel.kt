package com.example.sportapplication.ui.settings

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer

@HiltViewModel
class BatteryViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _batteryLevel = MutableLiveData<Int>()
    val batteryLevel: LiveData<Int> get() = _batteryLevel

    private val _batteryLevelFloat = MutableLiveData<Float>()

    var batterySampler: Timer
    val batterySamples = ArrayList<Float>()
    var averageBatteryDifference by mutableFloatStateOf(0f)

    init {
        monitorBatteryLevel()
        batterySampler =
            fixedRateTimer("batterySampler", false, 1000L, 10000L) { addBatteryAtLevel() }

    }

    private fun addBatteryAtLevel() {
        if (batterySamples.size > 6) {
            Log.i("BATTERYSAMPLES", batterySamples.size.toString())
            batterySamples.removeAt(0)
        }
        if (batteryLevel.value is Int) {
            batterySamples.add(_batteryLevelFloat.value!!.toFloat())
        }

        batteryDifference()

    }

    private fun batteryDifference() {
        val differences = ArrayList<Float>()
        if (batterySamples.size > 1)
            for (i in 0..<(batterySamples.size - 1)) {
                differences.add(batterySamples[i] - batterySamples[i + 1])
            }

        var sumDifferences = 0f


        differences.forEach() { difference -> sumDifferences += difference }

        Log.i("BATTERYDIFFERNECE", "${sumDifferences.toString()} ${differences.size}")
        if (differences.size > 0)
            averageBatteryDifference = sumDifferences / differences.size
    }

    private fun monitorBatteryLevel() {
        val batteryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                if (level != -1 && scale != -1) {
                    val batteryPct = (level / scale.toFloat() * 100).toInt()
                    _batteryLevelFloat.postValue(level / scale.toFloat() * 100)
                    _batteryLevel.postValue(batteryPct)
                }
            }
        }
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        getApplication<Application>().registerReceiver(batteryReceiver, intentFilter)
    }

    // Method to manually decrease the battery level by 10%
    fun decreaseBatteryLevel() {
        val currentLevel = _batteryLevel.value ?: 100
        _batteryLevel.value = (currentLevel - 10).coerceAtLeast(0)
    }
}
