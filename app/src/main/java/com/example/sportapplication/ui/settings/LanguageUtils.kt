package com.example.sportapplication.ui.settings

import android.app.Application
import android.content.res.Configuration
import android.os.LocaleList
import java.util.*

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
