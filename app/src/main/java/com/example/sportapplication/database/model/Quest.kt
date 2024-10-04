package com.example.sportapplication.database.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Quest (
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    val reward : Reward
)