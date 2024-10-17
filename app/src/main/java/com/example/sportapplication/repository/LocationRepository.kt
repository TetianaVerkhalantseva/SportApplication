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

    @SuppressLint("MissingPermission")
    fun getLastKnownUserLocation() {
        val priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        val locationRequest = LocationRequest.create()
        locationRequest.setInterval(1000L)
        locationRequest.setFastestInterval(500L)
        locationRequest.setPriority(priority)

        val locationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                locationResult.lastLocation?.let {
                //locationResult.locations?.firstOrNull()?.let {
                //locationResult.locations.forEach {
                    CoroutineScope(Dispatchers.IO).launch {
                        _userLocationState.emit(
                            it
                        )
                    }
                    Log.e(
                        "WatchingSomeStuff",
                        "latitude= ${it.latitude}, longitude = ${it.longitude}"
                    )
                }
            }

        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.myLooper()
        )
    }
}