package com.example.sportapplication.ui.settings

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _selectedLanguage = MutableLiveData<String>()
    val selectedLanguage: LiveData<String> get() = _selectedLanguage

    init {
        _selectedLanguage.value = sharedPreferences.getString("language_key", "en") ?: "en"
    }

    fun setLanguage(languageCode: String) {
        viewModelScope.launch {
            sharedPreferences.edit().putString("language_key", languageCode).apply()
            _selectedLanguage.value = languageCode
        }
    }
}