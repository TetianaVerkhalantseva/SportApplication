package com.example.sportapplication.database.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.gms.maps.model.LatLng

data class EventResponseBody(
    val id: Long,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,   // Icon for the map marker
    @StringRes val description: Int? = null,
    val locationId: Long,    // InterestingLocation associated with the event
    val startTime: Long,     // Start time of the event (in milliseconds)
    val duration: Long,      // Duration of the event (in milliseconds)
    val questsIds: List<Long>,
    val rewardItemId: Long?
) {

    var coordinate: LatLng? = null

    // Function to check if the event is active
    fun isActive(currentTime: Long): Boolean {
        return currentTime in startTime until (startTime + duration)
    }
}
