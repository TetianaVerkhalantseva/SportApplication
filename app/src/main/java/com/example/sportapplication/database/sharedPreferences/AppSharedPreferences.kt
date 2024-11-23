package com.example.sportapplication.database.sharedPreferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val USER_EXPERIENCE = "user_experience"
    }

    var userExperience: Long
        set(value)  {
            sharedPreferences.edit().putLong(USER_EXPERIENCE, userExperience + value).apply()
        }
        get() {
            return sharedPreferences.getLong(USER_EXPERIENCE, 0)
        }
}