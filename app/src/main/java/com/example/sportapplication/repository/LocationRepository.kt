package com.example.sportapplication.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    private val _userLocationState = MutableSharedFlow<Location>()
    val userLocationState = _userLocationState.asSharedFlow()

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission") // Suppresses missing permission warning for location access
    fun getLastKnownUserLocation() {
        // Define the priority for location accuracy, balanced between power usage and accuracy
        val priority = Priority.PRIORITY_BALANCED_POWER_ACCURACY

        // Build the location request with the given priority and interval settings
        val locationRequest = LocationRequest.Builder(priority, 1000L) // Request location every 1000 milliseconds
            .setMinUpdateIntervalMillis(500L) // Set the minimum interval between updates to 500 milliseconds
            .build()

        // Create a location callback to handle location updates
        val locationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                // If a location is available, emit it to the state and log the coordinates
                locationResult.lastLocation?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        _userLocationState.emit(it) // Update the user location state asynchronously
                    }

                    Log.e(
                        "WatchingSomeStuff",
                        "latitude= ${it.latitude}, longitude = ${it.longitude}"
                    )
                }
            }
        }

        // Request location updates with the built location request and callback, using the main thread looper
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.myLooper() // Ensure that callbacks are dispatched on the current thread's looper
        )
    }
}