package com.example.sportapplication.ui.settings

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.LocaleList
import java.util.*

/**
 * LocaleHelper is an object that provides utility methods for updating the locale configuration
 * of the application context to support language changes dynamically.
 */

// Wraps the given context with a new configuration that reflects the specified language.
object LocaleHelper {
    fun wrap(context: Context, languageCode: String): ContextWrapper {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        config.setLocales(LocaleList(locale))

        val newContext = context.createConfigurationContext(config)
        return ContextWrapper(newContext)
    }
}