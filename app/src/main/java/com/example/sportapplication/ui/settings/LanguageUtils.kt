package com.example.sportapplication.ui.settings

import android.app.Application
import android.content.res.Configuration
import android.os.LocaleList
import java.util.*

// Updates the app’s locale to a new language based on the languageCode parameter

fun Application.updateLocale(languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val config = Configuration(resources.configuration)
    config.setLocale(locale)
    config.setLocales(LocaleList(locale))

    applicationContext.resources.updateConfiguration(config, applicationContext.resources.displayMetrics)

    // Force update
    applicationContext.createConfigurationContext(config)
}
