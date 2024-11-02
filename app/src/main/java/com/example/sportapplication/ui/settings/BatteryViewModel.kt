package com.example.sportapplication.ui.settings

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BatteryViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _batteryLevel = MutableLiveData<Int>()
    val batteryLevel: LiveData<Int> get() = _batteryLevel

    init {
        monitorBatteryLevel()
    }

    private fun monitorBatteryLevel() {
        val batteryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                if (level != -1 && scale != -1) {
                    val batteryPct = (level / scale.toFloat() * 100).toInt()
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
