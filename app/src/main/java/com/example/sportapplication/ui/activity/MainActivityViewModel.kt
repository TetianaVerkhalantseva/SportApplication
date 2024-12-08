package com.example.sportapplication.ui.activity

import androidx.lifecycle.ViewModel
import com.example.sportapplication.database.dao.SensorDao
import com.example.sportapplication.repository.UserRepository
import com.example.sportapplication.utils.sensorPackage.SensorModel
import com.example.sportapplication.utils.sensorPackage.MultiSensor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository,
    multiSensor: MultiSensor,
    sensorDao: SensorDao
): ViewModel() {

    var sensors: SensorModel = SensorModel(multiSensor, sensorDao)

}