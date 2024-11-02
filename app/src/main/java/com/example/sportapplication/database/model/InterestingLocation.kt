package com.example.sportapplication.database.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


data class InterestingLocation(
    val id: Long,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    @StringRes val description: Int? = null,
    val latitude: Double,
    val longitude: Double
)
