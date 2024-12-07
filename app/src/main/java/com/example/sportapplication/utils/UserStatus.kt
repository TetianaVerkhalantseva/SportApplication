package com.example.sportapplication.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.sportapplication.R

enum class UserStatus(val experienceRequired: Long) {
    NEW(experienceRequired = 0),
    SKILLED(experienceRequired = 501),
    EXPERT(experienceRequired = 3001),
    PRO(experienceRequired = 5001);

    fun getUserStatusByExperience(experience: Long) =
        when {
            experience >= PRO.experienceRequired -> PRO
            experience >= EXPERT.experienceRequired -> EXPERT
            experience >= SKILLED.experienceRequired -> SKILLED
            experience >= NEW.experienceRequired -> NEW
            else -> NEW
        }

}

@Composable
fun getUserStatus(userExperience: Long): String =
    when (UserStatus.NEW.getUserStatusByExperience(userExperience)){
        UserStatus.NEW -> stringResource(id = R.string.status_v1)
        UserStatus.SKILLED -> stringResource(id = R.string.status_v2)
        UserStatus.EXPERT  -> stringResource(id = R.string.status_v3)
        UserStatus.PRO -> stringResource(id = R.string.status_v4)
    }
