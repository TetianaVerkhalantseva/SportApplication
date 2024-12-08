package com.example.sportapplication.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.database.dao.SensorDao
import com.example.sportapplication.repository.ItemRepository
import com.example.sportapplication.repository.UserRepository
import com.example.sportapplication.utils.sensorPackage.SensorModel
import com.example.sportapplication.utils.sensorPackage.MultiSensor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val multiSensor: MultiSensor,
    private val sensorDao: SensorDao,
    private val itemRepository: ItemRepository
): ViewModel() {

    var sensors: SensorModel = SensorModel(multiSensor, sensorDao)

    init {
        viewModelScope.launch {
            itemRepository.prepopulateItems()
        }
    }

}