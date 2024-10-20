package com.example.sportapplication.ui.settings

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnitViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _selectedUnitSystem = MutableLiveData<String>()
    val selectedUnitSystem: LiveData<String> get() = _selectedUnitSystem

    init {
        _selectedUnitSystem.value = sharedPreferences.getString("unit_system", "metric") ?: "metric"
    }

    fun setUnitSystem(unitSystem: String) {
        viewModelScope.launch {
            sharedPreferences.edit().putString("unit_system", unitSystem).apply()
            _selectedUnitSystem.value = unitSystem
        }
    }
}
