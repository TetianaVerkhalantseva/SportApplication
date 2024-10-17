package com.example.sportapplication.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository
): ViewModel() {

    val locationState = locationRepository.userLocationState
    fun startObservingUserLocation() {
        viewModelScope.launch {
            locationRepository.getLastKnownUserLocation()
        }
    }

    val requestLocationAccessState = MutableStateFlow(true).asStateFlow()

}