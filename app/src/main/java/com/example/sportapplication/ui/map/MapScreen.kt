package com.example.sportapplication.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView

@Composable
fun MapScreenRoute() {
    val viewModel : MapViewModel = hiltViewModel()

    MapScreen()
}

@Composable
fun MapScreen() {
    val context = LocalContext.current
    Column {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val mapView = MapView(context)

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