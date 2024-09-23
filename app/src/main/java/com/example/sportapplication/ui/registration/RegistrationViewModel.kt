package com.example.sportapplication.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapplication.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor (
    private val userRepository: UserRepository
): ViewModel() {

    private val _registrationState = MutableSharedFlow<RegistrationState>()
    val registrationState = _registrationState.asSharedFlow()


    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            _registrationState.emit(RegistrationState.InProgress)
            userRepository.createUserWithEmailAndPassword(email, password) {
                if (it.isSuccessful) {
                    viewModelScope.launch {
                        _registrationState.emit(RegistrationState.Success)
                    }
                } else {
                    viewModelScope.launch {
                        _registrationState.emit(RegistrationState.Error(it.exception))
                    }
                }
            }
        }
    }
}

sealed interface RegistrationState {
    data object Success: RegistrationState
    data class Error(val exception: Exception?): RegistrationState
    data object InProgress: RegistrationState
}