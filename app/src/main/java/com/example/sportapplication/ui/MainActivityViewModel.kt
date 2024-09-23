package com.example.sportapplication.ui

import androidx.lifecycle.ViewModel
import com.example.sportapplication.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val user = userRepository.currentUser
}