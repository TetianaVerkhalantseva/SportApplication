package com.example.sportapplication.ui.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RegistrationScreenRoute(
    navigateToMainScreen : () -> Unit
) {

    val viewModel : RegistrationViewModel = hiltViewModel()

    val registrationState by viewModel.registrationState.collectAsState(initial = null)

    RegistrationScreen(
        viewModel = viewModel,
        navigateToMainScreen = navigateToMainScreen,
        registrationState = registrationState
    )
}

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    navigateToMainScreen: () -> Unit,
    registrationState: RegistrationState?
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(key1 = registrationState) {
        when (registrationState) {
            RegistrationState.Success -> {
                navigateToMainScreen()
            }
            RegistrationState.InProgress -> {}
            is RegistrationState.Error -> {}
            null -> {}
        }
    }

    Column {

        TextField(value = email, onValueChange = {
            email = it
        })

        TextField(value = password, onValueChange = {
            password = it
        })
    }

    Button(onClick = { viewModel.registerUser(email, password) }) {

    }
}