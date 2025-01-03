package com.example.sportapplication.database.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.sportapplication.database.data.AchievementType

data class Achievement (
    val uid: Long,
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val type: AchievementType
)