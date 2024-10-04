package com.example.sportapplication.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapScreenRoute(
    navigateToSelectedMarkerScreen: () -> Unit
) {
    val viewModel : MapViewModel = hiltViewModel()

    MapScreen(
        navigateToSelectedMarkerScreen = navigateToSelectedMarkerScreen
    )
}

@Composable
fun MapScreen(
    navigateToSelectedMarkerScreen: () -> Unit
) {
    val geoPoint = GeoPoint(68.44147, 17.40685)
    val context = LocalContext.current
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