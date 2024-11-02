package com.example.sportapplication.ui.map

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.utils.isLocationPermissionGranted
import com.example.sportapplication.utils.locationPermissions
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sportapplication.R
import com.example.sportapplication.database.model.Event
import com.example.sportapplication.database.model.InterestingLocation
import com.example.sportapplication.database.model.Quest
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreenRoute(
    navigateToSelectedMarkerQuestScreen: () -> Unit,
    navigateToSelectedMarkerEventScreen: () -> Unit
) {
    val viewModel : MapViewModel = hiltViewModel()
    val requestLocationAccessState by viewModel.requestLocationAccessState.collectAsState()
    val userLocation by viewModel.locationState.collectAsState(initial = null)
    val followUserState by viewModel.followUserState.collectAsState()
    val interestingLocations by viewModel.interestingLocations.collectAsState()
    val quests by viewModel.quests.collectAsState()
    val events by viewModel.events.collectAsState()


    MapScreen(
        requestLocationAccessState = requestLocationAccessState,
        userLocation = userLocation,
        startObservingUserLocation = { viewModel.startObservingUserLocation() },
        followUserState = followUserState,
        interestingLocations = interestingLocations,
        quests = quests,
        events = events,
        navigateToSelectedMarkerQuestScreen = navigateToSelectedMarkerQuestScreen,
        navigateToSelectedMarkerEventScreen = navigateToSelectedMarkerEventScreen
    )
}

@Composable
fun MapScreen(
    requestLocationAccessState: Boolean,
    userLocation: Location?,
    startObservingUserLocation: () -> Unit,
    followUserState: Boolean,
    interestingLocations: List<InterestingLocation>,
    quests: List<Quest>,
    events: List<Event>,
    navigateToSelectedMarkerQuestScreen: () -> Unit,
    navigateToSelectedMarkerEventScreen: () -> Unit
) {

    val context = LocalContext.current
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.any { it.value }
        if (granted) { startObservingUserLocation() }
    }

    Log.e("WatchingSomeStuff", "Screen got UserLocation = $userLocation")

    LaunchedEffect(key1 = requestLocationAccessState) {
        if (context.isLocationPermissionGranted()) startObservingUserLocation()
        else locationPermissionLauncher.launch(locationPermissions)
    }

    OSMMapView(
        userLocation = userLocation,
        interestingLocations = interestingLocations,
        quests = quests,
        events = events,
        navigateToSelectedMarkerQuestScreen = navigateToSelectedMarkerQuestScreen,
        navigateToSelectedMarkerEventScreen = navigateToSelectedMarkerEventScreen
    )
}

@Composable
fun OSMMapView(
    userLocation: Location?,
    interestingLocations: List<InterestingLocation>,
    quests: List<Quest>,
    events: List<Event>,
    navigateToSelectedMarkerQuestScreen: () -> Unit,
    navigateToSelectedMarkerEventScreen: () -> Unit
) {

    var shouldCenterMap by remember { mutableStateOf(true) }  // To control centering on the user's interestingLocation
    var mapViewInitialized by remember { mutableStateOf(false) } // To check the initialization of the card

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val mapView = MapView(context)
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
                updateMarkerIcons(mapView, context, interestingLocations, quests, events, navigateToSelectedMarkerQuestScreen, navigateToSelectedMarkerEventScreen)

                mapView.addMapListener(object : MapListener {
                    override fun onZoom(event: ZoomEvent): Boolean {

                        // Update markers whenever the zoom level changes
                        updateMarkerIcons(mapView, context, interestingLocations, quests, events, navigateToSelectedMarkerQuestScreen, navigateToSelectedMarkerEventScreen)
                        return true // Return true to indicate the event was handled
                    }

                    override fun onScroll(event: ScrollEvent): Boolean {
                        // Do nothing when the map is scrolled, as only zoom events are of interest here
                        return false
                    }
                })

                // Adding an overlay for the user's location
                val myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mapView)
                myLocationOverlay.enableMyLocation()
                myLocationOverlay.enableFollowLocation()
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


        // Centers the map on the user's current location when clicked.
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
    }
}


// Updates map markers for interesting locations, quests, and events based on the zoom level.
// Removes markers at low zoom levels and adds clickable markers for quests, events, and locations without active quests or events.
fun updateMarkerIcons(
    mapView: MapView,
    context: Context,
    interestingLocations: List<InterestingLocation>,
    quests: List<Quest>,
    events: List<Event>,
    navigateToSelectedMarkerQuestScreen: () -> Unit,
    navigateToSelectedMarkerEventScreen: () -> Unit
) {
    if (mapView.zoomLevelDouble < 13) {
        // Remove markers if zoom level is less than 11
        mapView.overlays.removeAll { overlay ->
            overlay is Marker && (interestingLocations.any {
                it.latitude == overlay.position.latitude && it.longitude == overlay.position.longitude
            } || quests.any { quest ->
                quest.locationWithTasks.any { locationWithTasks ->
                    locationWithTasks.interestingLocation.latitude == overlay.position.latitude &&
                            locationWithTasks.interestingLocation.longitude == overlay.position.longitude
                }
            } || events.any { event ->
                interestingLocations.find { it.id == event.locationId }?.let {
                    it.latitude == overlay.position.latitude && it.longitude == overlay.position.longitude
                } ?: false
            })
        }
        return
    }

    // Process the quests first
    quests.forEach { quest ->
        quest.locationWithTasks.forEach { locationWithTasks ->
            val marker = Marker(mapView).apply {
                position = GeoPoint(locationWithTasks.interestingLocation.latitude, locationWithTasks.interestingLocation.longitude)
                icon = resizeBitmapDrawable(context.getDrawable(quest.icon), 64, 64) // size in pixels
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)

                setOnMarkerClickListener { marker, mapView ->
                    navigateToSelectedMarkerQuestScreen()
                    return@setOnMarkerClickListener true
                }
            }
            mapView.overlays.add(marker)
        }
    }

    // Next, process the events
    events.forEach { event ->
        val location = interestingLocations.find { it.id == event.locationId }

        location?.let {
            val marker = Marker(mapView).apply {
                position = GeoPoint(it.latitude, it.longitude)
                icon = resizeBitmapDrawable(context.getDrawable(event.icon), 64, 64)
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)

                setOnMarkerClickListener { marker, mapView ->
                    navigateToSelectedMarkerEventScreen()
                    return@setOnMarkerClickListener true
                }
            }
            mapView.overlays.add(marker)
        }
    }


    // Process interesting locations, but only if there is no quest or event on them.
    interestingLocations.forEach { location ->

        // Check if there is a quest or event at this location
        val hasQuestOrEvent = quests.any { quest ->
            quest.locationWithTasks.any { it.interestingLocation.id == location.id }
        } || events.any { event ->
            event.locationId == location.id
        }

        if (!hasQuestOrEvent) {

            // If there is no quest or event at the location, add a marker for an interesting location
            val marker = Marker(mapView).apply {
                position = GeoPoint(location.latitude, location.longitude)
                icon = resizeBitmapDrawable(context.getDrawable(location.icon), 64, 64)
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)

                setOnMarkerClickListener { marker, mapView ->
                    //TODO
                    return@setOnMarkerClickListener true
                }
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