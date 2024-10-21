package com.example.sportapplication.ui.settings

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel for managing the application's language settings
@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    // MutableLiveData to hold the currently selected language code (for example: "en" for English)
    private val _selectedLanguage = MutableLiveData<String>()
    val selectedLanguage: LiveData<String> get() = _selectedLanguage

    // Retrieve the saved language from SharedPreferences or default to "en" if none is set
    init {
        _selectedLanguage.value = sharedPreferences.getString("language_key", "en") ?: "en"
    }
    // Function to update the selected language and save it in SharedPreferences
    fun setLanguage(languageCode: String) {
        viewModelScope.launch {
            sharedPreferences.edit().putString("language_key", languageCode).apply()
            _selectedLanguage.value = languageCode
        }
    }
}