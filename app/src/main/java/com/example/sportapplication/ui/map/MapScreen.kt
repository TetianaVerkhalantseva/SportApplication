package com.example.sportapplication.ui.map

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.R
import com.example.sportapplication.database.entity.User
import com.example.sportapplication.database.model.EventQuest
import com.example.sportapplication.database.model.EventResponseBody
import com.example.sportapplication.database.model.InterestingLocation
import com.example.sportapplication.database.model.Quest
import com.example.sportapplication.repository.model.Event
import com.example.sportapplication.repository.model.QuestInProgress
import com.example.sportapplication.ui.introduction.IntroductionScreenRoute
import com.example.sportapplication.ui.settings.AvatarHelper
import com.example.sportapplication.utils.isLocationPermissionGranted
import com.example.sportapplication.utils.locationPermissions
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@Composable
fun MapScreenRoute(
    navigateToSelectedMarkerQuestScreen: (Long) -> Unit,
    navigateToSelectedMarkerEventScreen: (Long) -> Unit,
    navigateToProfileScreen: () -> Unit,
    setBottomBarVisibility: (Boolean) -> Unit,
    setSettingsVisibility: (Boolean) -> Unit
) {
    val viewModel : MapViewModel = hiltViewModel()
    val requestLocationAccessState by viewModel.requestLocationAccessState.collectAsState()
    val userLocation by viewModel.locationState.collectAsState(initial = null)
    val followUserState by viewModel.followUserState.collectAsState()
    val interestingLocations by viewModel.interestingLocations.collectAsState()
    val eventsQuests by viewModel.eventQuests.collectAsState()
    val events by viewModel.events.collectAsState()
    val achievedEvent by viewModel.achievedEvent.collectAsState()
    val eventsQuestLine by viewModel.eventsQuestline.collectAsState()
    val currentUnavailableQuestLineInLocation by viewModel.achievedEventQuestLineInAnotherLocation.collectAsState()
    val continueCompletingEventQuestDialog by viewModel.continueCompletingEventQuestDialog.collectAsState()
    val completedEventDialogState by viewModel.completedEventDialogState.collectAsState()
    val quests by viewModel.quests.collectAsState()
    val startCompletingQuestDialog by viewModel.startCompletingQuestDialog.collectAsState()
    val questInProgressDialog by viewModel.questInProgressDialog.collectAsState()
    val completedQuestDialog by viewModel.completedQuestDialog.collectAsState()
    val currentEventTimeOutMillis by viewModel.currentEventTimeOutMillis.collectAsState()
    val displayIntroductionPage by viewModel.displayIntroductionPage.collectAsState()
    val user by viewModel.user.collectAsState()
    val showSplash by viewModel.showSplash.collectAsState()

    LaunchedEffect(key1 = displayIntroductionPage) {
        setBottomBarVisibility(displayIntroductionPage.not())
    }

    MapScreen(
        requestLocationAccessState = requestLocationAccessState,
        user = user,
        showSplash = showSplash,
        userLocation = userLocation,
        startObservingUserLocation = { viewModel.startObservingUserLocation() },
        followUserState = followUserState,
        interestingLocations = interestingLocations,
        eventQuests = eventsQuests,
        eventResponseBodies = events,
        achievedEventResponseBody = achievedEvent,
        eventsQuestline = eventsQuestLine,
        navigateToSelectedMarkerQuestScreen = navigateToSelectedMarkerQuestScreen,
        navigateToSelectedMarkerEventScreen = navigateToSelectedMarkerEventScreen,
        currentUnavailableQuestLineInLocation = currentUnavailableQuestLineInLocation,
        continueCompletingEventQuestDialogResponseBody = continueCompletingEventQuestDialog,
        currentEventTimeOutMillis = currentEventTimeOutMillis,
        quests = quests,
        startCompletingQuestDialog = startCompletingQuestDialog,
        questInProgressDialog = questInProgressDialog,
        completedQuestDialog = completedQuestDialog,
        onConfirmCompletedQuestDialog = { viewModel.onConfirmCompletedQuestDialog() },
        onStartQuestClick = { viewModel.onStartQuestClick() },
        onDismissStartQuest = { viewModel.onDismissStartQuestDialog() },
        onQuestTaskCompleted = { viewModel.onQuestTaskComplete() },
        onDismissQuestInProgress = { viewModel.onDismissQuestInProgress() },
        onStartEventClick = { viewModel.onStartEventClick() },
        onDismissEventDialog = { viewModel.onDismissEventDialog() },
        onEventClick = {
            if (!viewModel.onEventClick(it))
                navigateToSelectedMarkerEventScreen(it.id)
        },
        onEventQuestComplete = { viewModel.onEventQuestComplete(it) },
        onDismissEventQuestlines = { viewModel.onDismissEventQuestlines() },
        onConfirmNotAvailableQuestLine = { viewModel.onConfirmNotAvailableQuestline() },
        onContinueCompletingEvent = { viewModel.onContinueCompletingEvent() },
        onDismissContinueCompletingEvent = { viewModel.onDismissContinueCompletingEvent() },
        completedEventDialogState = completedEventDialogState,
        onConfirmCompletedEventClick = { viewModel.onConfirmCompletedEventClick() },
        onQuestClick = {
            if (!viewModel.onQuestClick(it))
                navigateToSelectedMarkerQuestScreen(it.id)
        },
        displayIntroductionPage = displayIntroductionPage,
        onDismissIntroductionPage = { viewModel.onDismissIntroductionPage() },
        navigateToProfileScreen = navigateToProfileScreen,
        onDismissSplash = { viewModel.onDismissSplash() },
        setSettingsVisibility = setSettingsVisibility
    )
}

@Composable
fun MapScreen(
    user: User?,
    showSplash: Boolean,
    requestLocationAccessState: Boolean,
    userLocation: Location?,
    startObservingUserLocation: () -> Unit,
    followUserState: Boolean,
    interestingLocations: List<InterestingLocation>,
    eventQuests: List<EventQuest>,
    eventResponseBodies: List<EventResponseBody>,
    achievedEventResponseBody: Event?,
    eventsQuestline: List<EventsQuestline>?,
    currentUnavailableQuestLineInLocation: NotAvailableQuestLine?,
    continueCompletingEventQuestDialogResponseBody: EventResponseBody?,
    completedEventDialogState: CompletedEvent?,
    quests: List<Quest>,
    startCompletingQuestDialog: Quest?,
    questInProgressDialog: QuestInProgress?,
    completedQuestDialog: Quest?,
    currentEventTimeOutMillis: Long?,
    displayIntroductionPage: Boolean,
    onStartQuestClick: () -> Unit,
    onDismissStartQuest: () -> Unit,
    onQuestTaskCompleted: () -> Unit,
    onDismissQuestInProgress: () -> Unit,
    onConfirmCompletedQuestDialog: () -> Unit,
    navigateToSelectedMarkerQuestScreen: (Long) -> Unit,
    navigateToSelectedMarkerEventScreen: (Long) -> Unit,
    onStartEventClick: () -> Unit,
    onDismissEventDialog: () -> Unit,
    onEventClick: (EventResponseBody) -> Unit,
    onQuestClick: (Quest) -> Unit,
    onEventQuestComplete: (EventsQuestline) -> Unit,
    onDismissEventQuestlines: () -> Unit,
    onConfirmNotAvailableQuestLine: () -> Unit,
    onContinueCompletingEvent: () -> Unit,
    onDismissContinueCompletingEvent: () -> Unit,
    onConfirmCompletedEventClick: () -> Unit,
    onDismissIntroductionPage: () -> Unit,
    navigateToProfileScreen: () -> Unit,
    onDismissSplash: () -> Unit,
    setSettingsVisibility: (Boolean) -> Unit
) {

    val context = LocalContext.current
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.any { it.value }
        if (granted) { startObservingUserLocation() }
    }


    if (displayIntroductionPage.not()) {
        LaunchedEffect(key1 = requestLocationAccessState) {
            if (context.isLocationPermissionGranted()) startObservingUserLocation()
            else locationPermissionLauncher.launch(locationPermissions)
        }
    }


    completedQuestDialog?.let {
        CompletedQuestDialog(
            quest = it,
            onConfirmClick = onConfirmCompletedQuestDialog
        )
    }

    completedEventDialogState?.let {
        CompletedEventDialog(
            completedEvent = it,
            onConfirmClick = onConfirmCompletedEventClick

        )
    }
    questInProgressDialog?.let {
        QuestDialog(
            quest = it,
            onTaskCompleted = onQuestTaskCompleted,
            onDismiss = onDismissQuestInProgress
        )
    }

    startCompletingQuestDialog?.let {
        StartQuestDialog(
            quest = it,
            onStartClick = { onStartQuestClick() },
            onDismiss = { onDismissStartQuest() }
        )
    }
    continueCompletingEventQuestDialogResponseBody?.let {
        ContinueCompletingEventDialog(
            eventResponseBody = it,
            onContinueClick = onContinueCompletingEvent,
            onDismiss = onDismissContinueCompletingEvent
        )
    }

    if (currentUnavailableQuestLineInLocation != null) {
        NotAvailableQuestLineDialog(
            notAvailableQuestLine = currentUnavailableQuestLineInLocation,
            onConfirm = onConfirmNotAvailableQuestLine
        )
    }
    else if (eventsQuestline != null) {
        // Processing of quests and tasks
        eventsQuestline.find { it.isSelected }?.let { questLine ->
            questLine.eventQuest.
                locationWithTasks.getOrNull(questLine.locationWithTaskIndex ?: -1)
                ?.tasks?.getOrNull(questLine.taskIndex ?: -1)?.let { currentTask ->
                    EventQuestDialog(
                        eventQuest = questLine.eventQuest,
                        currentEventTimeOutMillis = currentEventTimeOutMillis,
                        currentTask = currentTask,
                        onTaskCompleted = { onEventQuestComplete(questLine) },
                        onDismiss = { onDismissEventQuestlines() }
                    )
                }

        }
    }
    else {
        achievedEventResponseBody?.let {
            EventDialog(
                eventResponseBody = it,
                onStartEventClick = onStartEventClick,
                onDismiss = onDismissEventDialog
            )
        }
    }

    OSMMapView(
        user = user,
        showSplash = showSplash,
        userLocation = userLocation,
        interestingLocations = interestingLocations,
        eventQuests = eventQuests,
        eventResponseBodies = eventResponseBodies,
        quests = quests,
        displayIntroductionPage = displayIntroductionPage,
        navigateToSelectedMarkerQuestScreen = navigateToSelectedMarkerQuestScreen,
        navigateToSelectedMarkerEventScreen = navigateToSelectedMarkerEventScreen,
        onEventClick = onEventClick,
        onQuestClick = onQuestClick,
        onDismissIntroductionPage = onDismissIntroductionPage,
        navigateToProfileScreen = navigateToProfileScreen,
        onDismissSplash = onDismissSplash,
        setSettingsVisibility = setSettingsVisibility
    )
}

@Composable
fun OSMMapView(
    user: User?,
    showSplash: Boolean,
    userLocation: Location?,
    interestingLocations: List<InterestingLocation>,
    eventQuests: List<EventQuest>,
    eventResponseBodies: List<EventResponseBody>,
    quests: List<Quest>,
    displayIntroductionPage: Boolean,
    navigateToSelectedMarkerQuestScreen: (Long) -> Unit,
    navigateToSelectedMarkerEventScreen: (Long) -> Unit,
    onEventClick: (EventResponseBody) -> Unit,
    onQuestClick: (Quest) -> Unit,
    onDismissIntroductionPage: () -> Unit,
    navigateToProfileScreen: () -> Unit,
    onDismissSplash: () -> Unit,
    setSettingsVisibility: (Boolean) -> Unit
) {
    val context = LocalContext.current

    val avatarId by AvatarHelper.avatarId.collectAsState()
    var globalMapView by remember {
        mutableStateOf<MapView?>(null)
    }

    val nicknameDynamic by AvatarHelper.nickname.collectAsState()
    val nickname = user?.name ?: nicknameDynamic

    var shouldCenterMap by remember { mutableStateOf(true) }  // To control centering on the user's interestingLocation
    var mapViewInitialized by remember { mutableStateOf(false) } // To check the initialization of the card

    var onZoomInterestingLocations by remember {
        mutableStateOf<List<InterestingLocation>>(emptyList())
    }
    var onZoomEventQuests by remember {
        mutableStateOf<List<EventQuest>>(emptyList())
    }
    var onZoomEvents by remember {
        mutableStateOf<List<EventResponseBody>>(emptyList())
    }
    var onZoomQuests by remember {
        mutableStateOf<List<Quest>>(emptyList())
    }

    LaunchedEffect(key1 = interestingLocations) {
        onZoomInterestingLocations = interestingLocations
    }

    LaunchedEffect(key1 = eventQuests) {
        onZoomEventQuests = eventQuests
    }

    LaunchedEffect(key1 = eventResponseBodies) {
        onZoomEvents = eventResponseBodies
    }


    LaunchedEffect(key1 = interestingLocations, key2 = eventQuests, key3 = eventResponseBodies) {
        globalMapView?.let {
            updateMarkerIcons(it, context, interestingLocations, eventQuests, quests, eventResponseBodies, navigateToSelectedMarkerQuestScreen, navigateToSelectedMarkerEventScreen, onEventClick, onQuestClick)
        }
    }

    LaunchedEffect(key1 = quests) {
        onZoomQuests = quests
        globalMapView?.let {
            updateMarkerIcons(it, context, interestingLocations, eventQuests, quests, eventResponseBodies, navigateToSelectedMarkerQuestScreen, navigateToSelectedMarkerEventScreen, onEventClick, onQuestClick)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val mapView = MapView(context)
                globalMapView = mapView
                mapView.setTileSource(TileSourceFactory.MAPNIK)
                mapView.setMultiTouchControls(true)

                // Prevent the map from repeating vertically
                mapView.isVerticalMapRepetitionEnabled = false
                mapView.setScrollableAreaLimitLatitude(
                    MapView.getTileSystem().maxLatitude,
                    MapView.getTileSystem().minLatitude,
                    0
                )

                // Setting up the card controller
                val mapController = mapView.controller
                mapController.setZoom(15.0)
                mapView.maxZoomLevel = 20.0
                mapView.minZoomLevel = 4.0


                // Call updateMarkerIcons to place markers on the map for interesting locations, quests, and events
                updateMarkerIcons(
                    mapView,
                    context,
                    interestingLocations,
                    eventQuests,
                    quests,
                    eventResponseBodies,
                    navigateToSelectedMarkerQuestScreen,
                    navigateToSelectedMarkerEventScreen,
                    onEventClick,
                    onQuestClick
                )

                mapView.addMapListener(object : MapListener {
                    override fun onZoom(event: ZoomEvent): Boolean {
                        // Update markers whenever the zoom level changes
                        updateMarkerIcons(
                            mapView,
                            context,
                            onZoomInterestingLocations,
                            onZoomEventQuests,
                            onZoomQuests,
                            onZoomEvents,
                            navigateToSelectedMarkerQuestScreen,
                            navigateToSelectedMarkerEventScreen,
                            onEventClick,
                            onQuestClick
                        )
                        return true // Return true to indicate the event was handled
                    }

                    override fun onScroll(event: ScrollEvent): Boolean {
                        // Do nothing when the map is scrolled, as only zoom events are of interest here
                        return false
                    }
                })

                // Adding an overlay for the user's location
                val myLocationOverlay =
                    MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
                myLocationOverlay.enableMyLocation()
                val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.location)
                myLocationOverlay.setDirectionIcon(bitmap)
                mapView.overlays.add(myLocationOverlay)

                mapViewInitialized = true // The map is initialized

                mapView
            },
            update = { mapView ->
                if (shouldCenterMap && userLocation != null) {
                    val geoPoint = GeoPoint(userLocation.latitude, userLocation.longitude)
                    mapView.controller.setCenter(geoPoint)
                    mapView.controller.setZoom(15.0)
                    shouldCenterMap = false
                }
            }
        )

        if (showSplash) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.8f))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = stringResource(R.string.change_your_settings_here),
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(onClick = {
                        onDismissSplash()
                    }) {
                        Text(stringResource(R.string.got_it))
                    }
                }
            }
        }

        if (displayIntroductionPage) {
            IntroductionScreenRoute(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                navigateToMapScreen = {
                    onDismissIntroductionPage()
                    if (showSplash)
                        setSettingsVisibility(true)
                }
            )
        }



        // Centers the map on the user's current location when clicked.
        if (displayIntroductionPage.not()) {
            FloatingActionButton(
                onClick = {
                    shouldCenterMap = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 100.dp, end = 16.dp), // Position in the lower right corner
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_navigation),
                    contentDescription = "Center on my location"
                )
            }

            // Will navigate to a selected marker when implemented.
            FloatingActionButton(
                onClick = { }, //TODO Implement navigation functionality
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp), // Position under the first button
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_navigate),
                    contentDescription = "Navigate to marker"
                )
            }

            // Overlay Avatar with Username
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Username over avatar
                    Text(
                        text = nickname,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.scrim,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    // Avatar with circular frame
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(
                                Color.White,
                                shape = CircleShape
                            )
                            .padding(5.dp)
                            .clip(CircleShape)
                            .clickable { navigateToProfileScreen() }
                    ) {
                        Image(
                            painter = painterResource(
                                id = when (avatarId) {
                                    0 -> R.drawable.avatar_female
                                    1 -> R.drawable.avatar_male
                                    else -> R.drawable.avatar_female
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }
        }
    }
}


// Updates map markers for interesting locations, quests, and events based on the zoom level.
// Removes markers at low zoom levels and adds clickable markers for quests, events, and locations without active quests or events.
fun updateMarkerIcons(
    mapView: MapView,
    context: Context,
    interestingLocations: List<InterestingLocation>,
    eventQuests: List<EventQuest>,
    quests: List<Quest>,
    eventResponseBodies: List<EventResponseBody>,
    navigateToSelectedMarkerQuestScreen: (Long) -> Unit,
    navigateToSelectedMarkerEventScreen: (Long) -> Unit,
    onEventClick: (EventResponseBody) -> Unit,
    onQuestClick: (Quest) -> Unit
) {
    mapView.overlays.forEach {
        if (it is Marker) mapView.overlays.remove(it)
    }

    if (mapView.zoomLevelDouble < 13) {
        // Remove markers if zoom level is less than 11
        mapView.overlays.removeAll { overlay ->
            overlay is Marker && (interestingLocations.any {
                it.latitude == overlay.position.latitude && it.longitude == overlay.position.longitude
            } || eventQuests.any { quest ->
                quest.locationWithTasks.any { locationWithTasks ->
                    locationWithTasks.interestingLocation.latitude == overlay.position.latitude &&
                            locationWithTasks.interestingLocation.longitude == overlay.position.longitude
                }
            } || eventResponseBodies.any { event ->
                interestingLocations.find { it.id == event.locationId }?.let {
                    it.latitude == overlay.position.latitude && it.longitude == overlay.position.longitude
                } ?: false
            })
        }
        return
    }

    // Process the events first
    val questsIdsToHide = mutableListOf<Long>()
    eventResponseBodies.forEach { event ->
        val location = interestingLocations.find { it.id == event.locationId }
        event.questsIds.firstOrNull()?.let {
            questsIdsToHide.add(it)
        }

        location?.let {
            val marker = Marker(mapView).apply {
                position = GeoPoint(it.latitude, it.longitude)
                icon = resizeBitmapDrawable(context.getDrawable(event.icon), 64, 64)
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)

                setOnMarkerClickListener { marker, mapView ->
                    onEventClick(event)
                    return@setOnMarkerClickListener true
                }
            }
            mapView.overlays.add(marker)
        }
    }

    // Next, process the events quests
    eventQuests.forEach { quest ->
        if (questsIdsToHide.contains(quest.id)) return@forEach
        quest.locationWithTasks.forEach { locationWithTasks ->
            val marker = Marker(mapView).apply {
                position = GeoPoint(locationWithTasks.interestingLocation.latitude, locationWithTasks.interestingLocation.longitude)
                icon = resizeBitmapDrawable(context.getDrawable(quest.icon), 64, 64) // size in pixels
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)

                setOnMarkerClickListener { marker, mapView ->
                    eventResponseBodies.find {
                        it.questsIds.contains(quest.id)
                    }?.let(onEventClick)
                    return@setOnMarkerClickListener true
                }
            }
            mapView.overlays.add(marker)
        }
    }

    quests.forEach { quest ->
        quest.locationWithTasks.let { locationWithTasks ->
            val marker = Marker(mapView).apply {
                position = GeoPoint(locationWithTasks.interestingLocation.latitude, locationWithTasks.interestingLocation.longitude)
                icon = resizeBitmapDrawable(context.getDrawable(quest.icon), 64, 64) // size in pixels
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)

                setOnMarkerClickListener { marker, mapView ->
                    onQuestClick(quest)
                    return@setOnMarkerClickListener true
                }
            }
            mapView.overlays.add(marker)
        }
    }

    // Process interesting locations, but only if there is no quest or event on them.
    interestingLocations.forEach { location ->

        // Check if there is a quest or event at this location
        val hasQuestOrEvent = eventQuests.any { quest ->
            quest.locationWithTasks.any { it.interestingLocation.id == location.id }
        } || eventResponseBodies.any { event ->
            event.locationId == location.id
        } || quests.any {  quest ->
            quest.locationWithTasks.interestingLocation.id == location.id
        }

        if (!hasQuestOrEvent) {
            // If there is no quest or event at the location, add a marker for an interesting location
            val marker = Marker(mapView).apply {
                position = GeoPoint(location.latitude, location.longitude)
                icon = resizeBitmapDrawable(context.getDrawable(location.icon), 64, 64)
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                title = context.getString(location.name)
            }
            mapView.overlays.add(marker)
        }
    }
}


// Function for resizing BitmapDrawable
fun resizeBitmapDrawable(drawable: Drawable?, width: Int, height: Int): Drawable {
    if (drawable is BitmapDrawable) {
        val bitmap = drawable.bitmap
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
        return BitmapDrawable(Resources.getSystem(), scaledBitmap)
    } else {
        // If the Drawable is not a BitmapDrawable, return it unchanged
        return drawable!!
    }
}