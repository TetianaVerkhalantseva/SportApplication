package com.example.sportapplication.ui.introduction

import androidx.lifecycle.ViewModel
import com.example.sportapplication.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

}