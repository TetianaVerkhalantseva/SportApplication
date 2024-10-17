package com.example.sportapplication.ui.map

import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapplication.utils.isLocationPermissionGranted
import com.example.sportapplication.utils.locationPermissions
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreenRoute(
    navigateToSelectedMarkerScreen: () -> Unit
) {
    val viewModel : MapViewModel = hiltViewModel()
    val requestLocationAccessState by viewModel.requestLocationAccessState.collectAsState()
    val userLocation by viewModel.locationState.collectAsState(initial = null)

    MapScreen(
        requestLocationAccessState = requestLocationAccessState,
        userLocation = userLocation,
        startObservingUserLocation = { viewModel.startObservingUserLocation() },
        navigateToSelectedMarkerScreen = navigateToSelectedMarkerScreen
    )
}

@Composable
fun MapScreen(
    requestLocationAccessState: Boolean,
    userLocation: Location?,
    startObservingUserLocation: () -> Unit,
    navigateToSelectedMarkerScreen: () -> Unit
) {

    val context = LocalContext.current
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.any { it.value }
        if (granted) { startObservingUserLocation() }
    }

    Log.e("WatchingSomeStuff", "Screen got location = $userLocation")

    LaunchedEffect(key1 = requestLocationAccessState) {
        if (context.isLocationPermissionGranted()) startObservingUserLocation()
        else locationPermissionLauncher.launch(locationPermissions)
    }

    val geoPoint = GeoPoint(68.44147, 17.40685)
    Column {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val mapView = MapView(context)
                val marker = Marker(mapView)
                marker.position = geoPoint
                marker.setOnMarkerClickListener { marker, mapView ->
                    navigateToSelectedMarkerScreen()
                    return@setOnMarkerClickListener true
                }
                mapView.overlays.add(marker)

                mapView.isVerticalMapRepetitionEnabled = false
                mapView.setScrollableAreaLimitLatitude(
                    MapView.getTileSystem().maxLatitude,
                    MapView.getTileSystem().minLatitude,
                    0
                )
                mapView.maxZoomLevel = 20.0
                mapView.minZoomLevel = 4.0

                mapView.setTileSource(TileSourceFactory.USGS_TOPO)
                mapView.setBuiltInZoomControls(true)
                mapView.setMultiTouchControls(true)
                mapView
            }
        )

    }
}