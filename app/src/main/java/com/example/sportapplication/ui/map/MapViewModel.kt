package com.example.sportapplication.ui.map

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.database.model.Event
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.database.sharedPreferences.AppSharedPreferences
import com.example.sportapplication.repository.LocationRepository
import com.example.sportapplication.repository.PoiRepository
import com.example.sportapplication.repository.UserRepository
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

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

    private val _lastDismissedDialogEvent = MutableStateFlow<Event?>(null)
    private val _achievedEvent = MutableStateFlow<Event?>(null)
    val achievedEvent = _achievedEvent.asStateFlow()

    private val _eventsQuestline = MutableStateFlow<List<EventsQuestline>?>(null)
    val eventsQuestline = _eventsQuestline.asStateFlow()

    private val _currentQuestProgress = MutableStateFlow<Pair<Long, Int>?>(null) // Хранит текущий квест (id) и индекс задачи
    val currentQuestProgress = _currentQuestProgress.asStateFlow()


    private var lastKnownLocation: Location? = null
    val locationState = locationRepository.userLocationState.map {
        lastKnownLocation = it
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
                    Log.e(
                        "HereToSee",
                        "location lat = ${location.latitude}, long = ${location.longitude}, event coordinate = $it"
                    )
                    val distance = SphericalUtil.computeDistanceBetween(
                        it,
                        LatLng(location.latitude, location.longitude)
                    )
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
            if (_achievedEvent.value == event
                || (_lastDismissedDialogEvent.value != null && event == _lastDismissedDialogEvent.value)
            )
                return@launch

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
            val event = _achievedEvent.value
            _achievedEvent.emit(null)
            event?.let {
                val eventsQuests = _quests.value.filter {
                    event.questsIds.contains(it.id)
                }
                _eventsQuestline.emit(
                    eventsQuests.mapIndexed { index, quest ->
                        val isSelected = index == 0

                        EventsQuestline(
                            quest = quest,
                            isSelected = isSelected,
                            locationWithTaskIndex = if (isSelected) 0 else null,
                            taskIndex = if (isSelected) 0 else null,
                            isInCurrentLocation = isSelected
                        )
                    }
                )
            }
        }
    }

    fun onDismissEventDialog() {
        viewModelScope.launch {
            val currentDialogEvent = _achievedEvent.value
            currentDialogEvent?.let {
                _lastDismissedDialogEvent.emit(it)
                _achievedEvent.emit(null)
            }
        }
    }

    fun onEventClick(event: Event): Boolean {
        event.coordinate?.let {
            if (isUserNearLocation(it, MINIMUM_DISTANCE_TO_EVENT)) {
                viewModelScope.launch {
                    _achievedEvent.emit(event)
                }
                return true
            }
        }
        return false
    }

    fun isUserNearLocation(coordinates: LatLng, requiredDistance: Double): Boolean {
        lastKnownLocation?.let { location ->
            coordinates.let {
                val distance = SphericalUtil.computeDistanceBetween(
                    it,
                    LatLng(location.latitude, location.longitude)
                )
                Log.e("123123", "distance to = $distance")
                return distance < requiredDistance
            }
        }
        return false
    }

    fun onEventQuestComplete(questline: EventsQuestline) {
        viewModelScope.launch {
            val currentQuestLines = _eventsQuestline.value
            var newTaskIndex : Int?  =
                if (questline.quest.locationWithTasks[questline.locationWithTaskIndex ?: 0].tasks.lastIndex == questline.taskIndex) 0
                else (questline.taskIndex ?: 0) + 1
            var locationWithTaskIndex =
                if (newTaskIndex == 0)
                    if (questline.quest.locationWithTasks.lastIndex == questline.locationWithTaskIndex) null
                    else (questline.locationWithTaskIndex ?: 0) + 1
                else questline.locationWithTaskIndex
            var isInCurrentLocation = false

            val currentSelectedQuestLineIndex = currentQuestLines?.indexOf(questline)
            val questLineIndex =
                if (locationWithTaskIndex == null) {
                    locationWithTaskIndex = 0
                    if (currentSelectedQuestLineIndex == currentQuestLines?.lastIndex) null
                    else (currentSelectedQuestLineIndex ?: 0) + 1
                }
                else currentSelectedQuestLineIndex

            if (locationWithTaskIndex != questline.locationWithTaskIndex || currentSelectedQuestLineIndex != questLineIndex) {
                currentQuestLines?.getOrNull(
                    questLineIndex ?: -1
                )?.let {
                    it.quest.locationWithTasks.get(locationWithTaskIndex).let {
                        isInCurrentLocation = isUserNearLocation(
                            LatLng(
                                it.interestingLocation.latitude,
                                it.interestingLocation.longitude
                            ),
                            requiredDistance = MINIMUM_DISTANCE_TO_EVENT
                        )

                    }
                }
            }
            else isInCurrentLocation = questline.isInCurrentLocation

            Log.e("123123", "taskIndex = $newTaskIndex, locationIndex = $locationWithTaskIndex, questlineIndex = $currentSelectedQuestLineIndex, questLineIndex = $questLineIndex")
            currentQuestLines?.let { currentEventsQuestline ->
                _eventsQuestline.emit(
                    currentEventsQuestline.mapIndexed { index, eventsQuestline ->
                        val isSelected = index == questLineIndex
                        eventsQuestline.copy(
                            isSelected = isSelected,
                            locationWithTaskIndex =
                                if (isSelected) locationWithTaskIndex
                                else 0,
                            taskIndex =
                                if (isSelected) newTaskIndex
                                else 0
                        )
                    }
                )
            }

        }
    }

    fun onDismissEventQuestlines() {
        TODO("Not yet implemented")
    }

}

data class EventsQuestline(
    val quest: Quest,
    val isSelected: Boolean,
    val locationWithTaskIndex: Int?,
    val taskIndex: Int?,
    val isInCurrentLocation: Boolean
)