package com.example.sportapplication.mapper

import com.example.sportapplication.database.model.Achievement
import com.example.sportapplication.repository.model.AchievementUI

fun Achievement.toUi(isCompleted: Boolean) =
    AchievementUI(
        uid = uid,
        icon = icon,
        title = title,
        type = type,
        isCompleted = isCompleted
    )