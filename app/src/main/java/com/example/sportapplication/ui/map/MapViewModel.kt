package com.example.sportapplication.ui.map

import android.location.Location
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.database.data.EVENT_REWARD_MULTIPLIER
import com.example.sportapplication.database.entity.AchievedEvent
import com.example.sportapplication.database.model.EventQuest
import com.example.sportapplication.database.model.EventResponseBody
import com.example.sportapplication.database.model.LocationWithTasks
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.database.sharedPreferences.AppSharedPreferences
import com.example.sportapplication.mapper.toEvent
import com.example.sportapplication.mapper.toQuest
import com.example.sportapplication.mapper.toQuestInProgress
import com.example.sportapplication.mapper.toResponseBody
import com.example.sportapplication.repository.LocationRepository
import com.example.sportapplication.repository.PoiRepository
import com.example.sportapplication.repository.UserRepository
import com.example.sportapplication.repository.model.Event
import com.example.sportapplication.repository.model.QuestInProgress
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import com.example.sportapplication.database.dao.UserDao
import kotlinx.coroutines.launch
import com.example.sportapplication.database.entity.User
import java.util.Calendar
import javax.inject.Inject

private const val MINIMUM_DISTANCE_TO_EVENT = 300.0

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val poiRepository: PoiRepository,
    private val userRepository: UserRepository,
    private val userDao: UserDao,
    private val prefs: AppSharedPreferences
): ViewModel() {
    private var completedEventsIds: List<AchievedEvent> = emptyList()

    private val _displayIntroductionPage = MutableStateFlow(isMapScreenFirstLaunch)
    val displayIntroductionPage =_displayIntroductionPage.asStateFlow()

    private val _showSplash = MutableStateFlow(prefs.showSplash)
    val showSplash = _showSplash.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _followUserState = MutableStateFlow(true)
    val followUserState = _followUserState.asStateFlow()

    private val _interestingLocations = MutableStateFlow(poiRepository.getLocations())
    val interestingLocations = _interestingLocations.asStateFlow()

    private val _quests = MutableStateFlow<List<Quest>>(emptyList())
    val quests = _quests.asStateFlow()

    private val _eventsQuests = MutableStateFlow(poiRepository.getEventsQuests())
    val eventQuests = _eventsQuests.asStateFlow()

    private val _events = MutableStateFlow<List<EventResponseBody>>(emptyList())
    val events = _events.asStateFlow()

    private val _lastDismissedDialogEvent = MutableStateFlow<Event?>(null)

    private var _currentEventInProgress : EventResponseBody? = null
    private var _currentSavedAchievedEvent : EventResponseBody? = null
    private val _currentEventTimeOutMillis = MutableStateFlow<Long?>(null)
    val currentEventTimeOutMillis = _currentEventTimeOutMillis.asStateFlow()
    private val _achievedEvent = MutableStateFlow<Event?>(null)
    val achievedEvent = _achievedEvent.asStateFlow()

    private var _currentSavedEventsQuestLine : List<EventsQuestline>? = null
    private val _eventsQuestline = MutableStateFlow<List<EventsQuestline>?>(null)
    val eventsQuestline = _eventsQuestline.asStateFlow()

    private var _savedAchievedEventQuestLineInAnotherLocation : NotAvailableQuestLine? = null
    private val _achievedEventQuestLineInAnotherLocation = MutableStateFlow<NotAvailableQuestLine?>(null)
    val achievedEventQuestLineInAnotherLocation = _achievedEventQuestLineInAnotherLocation.asStateFlow()

    private val _continueCompletingEventQuestDialog = MutableStateFlow<EventResponseBody?>(null)
    val continueCompletingEventQuestDialog = _continueCompletingEventQuestDialog.asStateFlow()

    private var _completedEventDialogState = MutableStateFlow<CompletedEvent?>(null)
    val completedEventDialogState = _completedEventDialogState.asStateFlow()

    private var lastKnownLocation: Location? = null

    private var _previousDismissedQuestDialog : Quest? = null
    private val _startCompletingQuestDialog = MutableStateFlow<Quest?>(null)
    val startCompletingQuestDialog = _startCompletingQuestDialog.asStateFlow()
    private val _questInProgressDialog = MutableStateFlow<QuestInProgress?>(null)
    val questInProgressDialog = _questInProgressDialog.asStateFlow()

    private val _completedQuestDialog = MutableStateFlow<Quest?>(null)
    val completedQuestDialog = _completedQuestDialog.asStateFlow()

    val locationState = locationRepository.userLocationState.map {
        lastKnownLocation = it
        onUserLocationUpdate(it)
        it
    }

    private fun onUserLocationUpdate(location: Location) {
        viewModelScope.launch {
            handleIfUserIsNearEvent(location)
            handleIfUserIsNearEventQuest(location)
            handleIfUserIsNearQuest(location)
        }
    }

    private fun handleIfUserIsNearQuest(location: Location) {
        viewModelScope.launch {
            val quests = _quests.value
            if (quests.isEmpty()) return@launch
            quests.forEach { quest ->
                quest.locationWithTasks.let {
                    val distance =
                        SphericalUtil.computeDistanceBetween(
                            LatLng(it.interestingLocation.latitude, it.interestingLocation.longitude),
                            LatLng(location.latitude, location.longitude)
                        )
                    if (distance <= MINIMUM_DISTANCE_TO_EVENT) {
                        doOnQuestReached(quest)
                    }
                }
            }

        }
    }

    private fun handleIfUserIsNearEventQuest(location: Location) {
        viewModelScope.launch {
            val quests = _eventsQuests.value
            if (quests.isEmpty()) return@launch
            quests.forEach { quest ->
                quest.locationWithTasks.forEach {
                    val distance =
                        SphericalUtil.computeDistanceBetween(
                            LatLng(it.interestingLocation.latitude, it.interestingLocation.longitude),
                            LatLng(location.latitude, location.longitude)
                        )
                    if (distance <= MINIMUM_DISTANCE_TO_EVENT) {
                        doOnEventQuestReached(quest)
                    }
                }
            }

        }
    }

    private fun doOnEventQuestReached(eventQuest: EventQuest) {
        viewModelScope.launch {
            val nextLocationWithTaskToContinueComplete =_currentSavedEventsQuestLine?.find { it.isSelected }?.getCurrentLocationWithTask()

            if (nextLocationWithTaskToContinueComplete != null
                && eventQuest.locationWithTasks.contains(nextLocationWithTaskToContinueComplete)
                &&  _eventsQuestline.value == null
                ) {
                _currentSavedAchievedEvent?.let {
                    _continueCompletingEventQuestDialog.emit(it)
                }
            }
        }
    }

    private fun doOnQuestReached(quest: Quest) {
        viewModelScope.launch {
            if (_previousDismissedQuestDialog == quest) return@launch
            _startCompletingQuestDialog.emit(quest)
        }
    }


    private fun handleIfUserIsNearEvent(location: Location) {
        viewModelScope.launch {
            val events = _events.value
            if (events.isEmpty()) return@launch
            events.forEach { event ->
                if (completedEventsIds.find { it.id == event.id.toString() } == null) {
                    event.coordinate?.let {
                        val distance = SphericalUtil.computeDistanceBetween(
                            it,
                            LatLng(location.latitude, location.longitude)
                        )
                        if (distance <= MINIMUM_DISTANCE_TO_EVENT && event != _currentSavedAchievedEvent) {
                            doOnEventReached(event)
                            return@launch
                        }
                    }
                }
            }
        }
    }

    private fun doOnEventReached(eventResponseBody: EventResponseBody) {
        viewModelScope.launch {
            if (_achievedEvent.value?.id == eventResponseBody.id
                || (_lastDismissedDialogEvent.value != null && eventResponseBody.id == _lastDismissedDialogEvent.value?.id)
            )
                return@launch
            val isCompleted = completedEventsIds.find { it.id == eventResponseBody.id.toString() } != null
            _achievedEvent.emit(eventResponseBody.toEvent(isCompleted))
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
        observeCompletedEvents()
        observeCompletedQuests()
        observeUser()
        viewModelScope.launch {
            val events = userRepository.getAllNotAchievedEvents()
            val currentAvailableEventResponseBodies = mutableListOf<EventResponseBody>()
            events.forEach { event ->
                _interestingLocations.value.find { it.id == event.locationId }?.let {
                    event.coordinate = LatLng(it.latitude, it.longitude)
                }

                val isEventAvailableRightNow = isEventAvailable(event)
                if (isEventAvailableRightNow)
                    currentAvailableEventResponseBodies.add(event)
            }
            filterAvailableEventsQuests(currentAvailableEventResponseBodies.map { it.id })
            _events.emit(currentAvailableEventResponseBodies)
        }
        getAndSetQuests()
    }

    private fun observeUser() {
        userDao.getUserLiveData().observeForever { user ->
            viewModelScope.launch {
                _user.emit(user)
            }
        }
    }

    private fun getAndSetQuests() {
        viewModelScope.launch {
            val quests = userRepository.getAllNotAchievedQuests()
            _quests.emit(quests)
        }
    }

    private fun observeCompletedEvents() {
        viewModelScope.launch {
            userRepository.getAllAchievedEventsLiveData().observeForever { achievedEvents ->
                if (_events.value.isEmpty()) return@observeForever

                val achievedEventsIds = achievedEvents.map { it.id }
                completedEventsIds = achievedEvents
                viewModelScope.launch {
                    val newEvents = _events.value.filter { !achievedEventsIds.contains(it.id.toString()) }
                    filterAvailableEventsQuests(newEvents.map { it.id })
                    _events.emit(newEvents)
                }
            }
        }
    }

    private fun observeCompletedQuests() {
        viewModelScope.launch {
            userRepository.getAllAchievedQuestsLiveData().observeForever { achievedQuests ->
                if (_quests.value.isEmpty()) return@observeForever
                val achievedQuestsIds = achievedQuests.map { it.id }

                //completedEventsIds = achievedEvents
                viewModelScope.launch {
                    val newQuests = _quests.value.filter { !achievedQuestsIds.contains(it.id) }
                    _quests.emit(newQuests)
                }
            }
        }
    }

    private fun filterAvailableEventsQuests(availableEventsIds: List<Long>) {
        viewModelScope.launch {
            val newEventsQuests = _eventsQuests.value.filter {
                availableEventsIds.contains(it.eventId)
            }
            _eventsQuests.emit(newEventsQuests)
        }
    }

    private fun isEventAvailable(eventResponseBody: EventResponseBody): Boolean {
        val cal = Calendar.getInstance()
        return cal.timeInMillis >= eventResponseBody.startTime && cal.timeInMillis < (eventResponseBody.startTime + eventResponseBody.duration)
    }

    fun onStartEventClick() {
        viewModelScope.launch {
            val event = _achievedEvent.value
            _currentEventInProgress = event?.toResponseBody()
            _currentEventTimeOutMillis.emit(event?.startTime?.plus(event.duration))
            _achievedEvent.emit(null)
            event?.let {
                val eventsQuests = _eventsQuests.value.filter {
                    event.questsIds.contains(it.id)
                }
                _eventsQuestline.emit(
                    eventsQuests.mapIndexed { index, quest ->
                        val isSelected = index == 0

                        EventsQuestline(
                            eventQuest = quest,
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

    fun onStartQuestClick() {
        viewModelScope.launch {
            _startCompletingQuestDialog.value?.let { quest ->
                _previousDismissedQuestDialog = quest
                _startCompletingQuestDialog.emit(null)
                _questInProgressDialog.emit(quest.toQuestInProgress())
            }
        }
    }

    fun onDismissStartQuestDialog() {
        viewModelScope.launch {
            _startCompletingQuestDialog.value?.let { quest ->
                _previousDismissedQuestDialog = quest
                _startCompletingQuestDialog.emit(null)
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

    fun onEventClick(eventResponseBody: EventResponseBody): Boolean {
        eventResponseBody.coordinate?.let {
            if (isUserNearLocation(it, MINIMUM_DISTANCE_TO_EVENT)) {
                val isEventCompleted = completedEventsIds.find { it.id == eventResponseBody.id.toString() } != null
                viewModelScope.launch {
                    _achievedEvent.emit(eventResponseBody.toEvent(isEventCompleted))
                }
                return true
            }
        }
        return false
    }

    fun onQuestClick(quest: Quest): Boolean {
        quest.locationWithTasks.interestingLocation.let { location ->
            if (isUserNearLocation(LatLng(location.latitude, location.longitude), MINIMUM_DISTANCE_TO_EVENT)) {
                viewModelScope.launch {
                    _startCompletingQuestDialog.emit(quest)
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
                return distance < requiredDistance
            }
        }
        return false
    }

    fun distanceFromUserToCoordinates(coordinates: LatLng): Double? {
        lastKnownLocation?.let { location ->
            coordinates.let {
                return SphericalUtil.computeDistanceBetween(
                    it,
                    LatLng(location.latitude, location.longitude)
                )
            }
        }
        return null
    }

    fun onQuestTaskComplete() {
        viewModelScope.launch {
            _questInProgressDialog.value?.let {  quest ->
                val indexOfCurrentCompletedTask = quest.locationWithTasks.tasks.indexOfFirst { it.isInProgress }
                if (indexOfCurrentCompletedTask > -1) {
                    if (indexOfCurrentCompletedTask == quest.locationWithTasks.tasks.lastIndex) {
                        val quest = quest.toQuest()
                        _questInProgressDialog.emit(null)
                        _completedQuestDialog.emit(quest)

                        userRepository.insertAchievedQuest(quest.id)
                        prefs.userExperience = quest.reward.experience
                    }
                    else {
                        _questInProgressDialog.emit(
                            quest.copy(
                                locationWithTasks = quest.locationWithTasks.copy(
                                    tasks = quest.locationWithTasks.tasks.mapIndexed { index, taskInProgress ->
                                        taskInProgress.copy(
                                            isInProgress = index == indexOfCurrentCompletedTask + 1
                                        )
                                    }
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    fun onDismissQuestInProgress() {
        viewModelScope.launch {
            _questInProgressDialog.emit(null)
        }
    }


    fun onEventQuestComplete(questline: EventsQuestline) {
        viewModelScope.launch {
            val currentQuestLines = _eventsQuestline.value
            val currentSelectedQuestLineIndex = currentQuestLines?.indexOf(questline)
            val previousQuestLine = currentQuestLines?.getOrNull(currentSelectedQuestLineIndex ?: -1)
            previousQuestLine?.let {
                if (currentSelectedQuestLineIndex == currentQuestLines?.lastIndex
                    && it.eventQuest.locationWithTasks.lastIndex == it.locationWithTaskIndex
                    && it.eventQuest.locationWithTasks.getOrNull(it.locationWithTaskIndex)?.tasks?.lastIndex == it.taskIndex) {
                    _eventsQuestline.emit(null)
                    _currentSavedAchievedEvent = null
                    _currentSavedEventsQuestLine = null
                    _savedAchievedEventQuestLineInAnotherLocation = null

                    var totalReward = 0L
                    currentQuestLines.forEach {
                        totalReward += it.eventQuest.reward.experience
                    }
                    totalReward *= EVENT_REWARD_MULTIPLIER

                    _lastDismissedDialogEvent.emit(null)
                    _currentEventInProgress?.let {
                        userRepository.insertAchievedEvent(it.id.toString())
                    }

                    _completedEventDialogState.emit(
                        CompletedEvent(
                            reward = totalReward
                        )
                    )
                    prefs.userExperience = totalReward
                    return@launch
                }
            }


            var newTaskIndex : Int?  =
                if (questline.eventQuest.locationWithTasks[questline.locationWithTaskIndex ?: 0].tasks.lastIndex == questline.taskIndex) 0
                else (questline.taskIndex ?: 0) + 1
            var locationWithTaskIndex =
                if (newTaskIndex == 0)
                    if (questline.eventQuest.locationWithTasks.lastIndex == questline.locationWithTaskIndex) null
                    else (questline.locationWithTaskIndex ?: 0) + 1
                else questline.locationWithTaskIndex
            var isInCurrentLocation = false


            val questLineIndex =
                if (locationWithTaskIndex == null) {
                    locationWithTaskIndex = 0
                    if (currentSelectedQuestLineIndex == currentQuestLines?.lastIndex) null
                    else (currentSelectedQuestLineIndex ?: 0) + 1
                }
                else currentSelectedQuestLineIndex

            val currentQuestLine = currentQuestLines?.getOrNull(questLineIndex ?: -1)

            if (locationWithTaskIndex != questline.locationWithTaskIndex || currentSelectedQuestLineIndex != questLineIndex) {
                currentQuestLine?.let {
                    it.eventQuest.locationWithTasks.get(locationWithTaskIndex).let {
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
            else {
                isInCurrentLocation = true
            }

            val valueToEmit =
                currentQuestLines?.mapIndexed { index, eventsQuestline ->
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

            if (isInCurrentLocation.not()) {
                _eventsQuestline.emit(null)
                if (_achievedEvent.value != null)  //@TODO REMOVE THIS
                    _currentSavedAchievedEvent = _achievedEvent.value?.toResponseBody()
                _achievedEvent.emit(null)
                _currentSavedEventsQuestLine = valueToEmit

                val distance = currentQuestLine?.eventQuest?.locationWithTasks?.get(locationWithTaskIndex)?.let {
                     distanceFromUserToCoordinates(
                        LatLng(
                            it.interestingLocation.latitude,
                            it.interestingLocation.longitude
                        )
                    )
                }

                var questId : Long? = null

                val currentInterestingLocation = valueToEmit?.find {
                    it.isSelected
                }?.let { questline ->
                    questId = questline.eventQuest.id
                    questline.eventQuest.locationWithTasks[questline.locationWithTaskIndex ?: -1]
                }

                distance?.let {
                    questId?.let {
                        currentInterestingLocation?.let {
                            _achievedEventQuestLineInAnotherLocation.emit(
                                NotAvailableQuestLine(
                                    questId = questId!!,
                                    locationName = it.interestingLocation.name,
                                    distance = distance
                                )
                            )
                        }
                    }
                }
            }
            else {
                _eventsQuestline.emit(valueToEmit)
            }
        }
    }

    fun onDismissEventQuestlines() {
        viewModelScope.launch {
            onDismissEventDialog()
            _eventsQuestline.emit(null)
        }
    }

    fun onConfirmNotAvailableQuestline() {
        viewModelScope.launch {
            _savedAchievedEventQuestLineInAnotherLocation = _achievedEventQuestLineInAnotherLocation.value
            _achievedEventQuestLineInAnotherLocation.emit(null)
        }
    }

    fun onContinueCompletingEvent() {
        viewModelScope.launch {
            _continueCompletingEventQuestDialog.emit(null)
            _eventsQuestline.emit(_currentSavedEventsQuestLine)
        }
    }

    fun onDismissContinueCompletingEvent() {
        viewModelScope.launch {
            _continueCompletingEventQuestDialog.emit(null)
        }
    }

    fun onConfirmCompletedQuestDialog() {
        viewModelScope.launch {
            _completedQuestDialog.emit(null)
        }
    }

    fun onConfirmCompletedEventClick() {
        viewModelScope.launch {
            _completedEventDialogState.emit(null)
        }
    }

    fun onDismissIntroductionPage() {
        viewModelScope.launch {
            _displayIntroductionPage.emit(false)
            isMapScreenFirstLaunch = false
        }
    }

    fun onDismissSplash() {
        viewModelScope.launch {
            _showSplash.emit(false)
            prefs.showSplash = false
        }
    }

    companion object {
        var isMapScreenFirstLaunch = true
    }

}

data class EventsQuestline(
    val eventQuest: EventQuest,
    val isSelected: Boolean,
    val locationWithTaskIndex: Int?,
    val taskIndex: Int?,
    val isInCurrentLocation: Boolean
) {
    fun getCurrentLocationWithTask() : LocationWithTasks? {
        return eventQuest.locationWithTasks.getOrNull(locationWithTaskIndex ?: -1)
    }
}

data class NotAvailableQuestLine(
    val questId: Long,
    @StringRes val locationName: Int,
    val distance: Double?
)

data class CompletedEvent(
    val reward: Long,
)