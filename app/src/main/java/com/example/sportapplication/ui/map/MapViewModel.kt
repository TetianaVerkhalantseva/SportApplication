package com.example.sportapplication.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.sportapplication.repository.PoiRepository

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val poiRepository: PoiRepository
): ViewModel() {

    private val _followUserState = MutableStateFlow(true)
    val followUserState = _followUserState.asStateFlow()

    private val _interestingLocations = MutableStateFlow(poiRepository.getLocations())
    val interestingLocations = _interestingLocations.asStateFlow()

    private val _quests = MutableStateFlow(poiRepository.getQuests())
    val quests = _quests.asStateFlow()

    private val _events = MutableStateFlow(poiRepository.getEvents())
    val events = _events.asStateFlow()


    val locationState = locationRepository.userLocationState

    fun startObservingUserLocation() {
        viewModelScope.launch {
            locationRepository.getLastKnownUserLocation()
        }
    }

    fun setFollowUser(value: Boolean) {
        viewModelScope.launch {
            _followUserState.emit(value)
        }
    }

    val requestLocationAccessState = MutableStateFlow(true).asStateFlow()

}