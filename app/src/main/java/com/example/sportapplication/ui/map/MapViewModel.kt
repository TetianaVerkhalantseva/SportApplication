package com.example.sportapplication.ui.map

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.database.model.Event
import com.example.sportapplication.database.sharedPreferences.AppSharedPreferences
import com.example.sportapplication.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.sportapplication.repository.PoiRepository
import com.example.sportapplication.repository.UserRepository
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Calendar

private const val MINIMUM_DISTANCE_TO_EVENT = 300.0

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val poiRepository: PoiRepository,
    private val userRepository: UserRepository,
    private val prefs: AppSharedPreferences
): ViewModel() {

    private val _followUserState = MutableStateFlow(true)
    val followUserState = _followUserState.asStateFlow()

    private val _interestingLocations = MutableStateFlow(poiRepository.getLocations())
    val interestingLocations = _interestingLocations.asStateFlow()

    private val _quests = MutableStateFlow(poiRepository.getQuests())
    val quests = _quests.asStateFlow()

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events = _events.asStateFlow()

    private val _achievedEvent = MutableStateFlow<Event?>(null)
    val achievedEvent = _achievedEvent.asStateFlow()


    val locationState = locationRepository.userLocationState.map {
        onUserLocationUpdate(it)
        it
    }

    private fun onUserLocationUpdate(location: Location) {
        viewModelScope.launch {
            handleIfUserIsNearEvent(location)
        }
    }

    private fun handleIfUserIsNearEvent(location: Location) {
        viewModelScope.launch {
            val events = _events.value
            if (events.isEmpty()) return@launch
            events.forEach { event ->
                Log.e("HereToSee", "event coord = ${event.coordinate}")
                event.coordinate?.let {
                    Log.e("HereToSee", "location lat = ${location.latitude}, long = ${location.longitude}, event coordinate = $it")
                    val distance = SphericalUtil.computeDistanceBetween(it, LatLng(location.latitude, location.longitude))
                    if (distance < MINIMUM_DISTANCE_TO_EVENT) {
                        doOnEventReached(event)
                        return@launch
                    }
                    Log.e("HereToSee", "Distance to event = $distance")
                }
            }
        }
    }

    private fun doOnEventReached(event: Event) {
        viewModelScope.launch {
            if (_achievedEvent.value == event) return@launch
            //userRepository.insertAchievedEvent(event.id.toString())
            //prefs.userExperience = event.reward.experience
            _achievedEvent.emit(event)
        }
    }

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

    init {
        viewModelScope.launch {
            //val events = poiRepository.getEvents()
            val events = userRepository.getAllNotAchievedEvents()
            Log.e("HereToSee", "events got = $events")
            val currentAvailableEvents = mutableListOf<Event>()
            events.forEach { event ->
                _interestingLocations.value.find { it.id == event.locationId }?.let {
                    event.coordinate = LatLng(it.latitude, it.longitude)
                }

                val isEventAvailableRightNow = isEventAvailable(event)
                Log.e("WatchingSomeStuff", "iseventAvailable = ${isEventAvailableRightNow}")
                if (isEventAvailableRightNow)
                    currentAvailableEvents.add(event)
            }
            _events.emit(currentAvailableEvents)
        }
    }

    private fun isEventAvailable(event: Event): Boolean {
        val cal = Calendar.getInstance()
        return cal.timeInMillis >= event.startTime && cal.timeInMillis < (event.startTime + event.duration)
    }

    fun onStartEventClick() {
        viewModelScope.launch {
            _achievedEvent.emit(null)
        }
    }

    fun onDismissEventDialog() {
        viewModelScope.launch {
            _achievedEvent.emit(null)
        }
    }

}