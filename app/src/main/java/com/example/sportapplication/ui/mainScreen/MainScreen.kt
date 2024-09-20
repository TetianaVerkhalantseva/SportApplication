package com.example.sportapplication.ui.mainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreenRoute() {
    val viewModel : MainViewModel = hiltViewModel()

    MainScreen()
}

@Composable
fun MainScreen() {
    Column {
        Text(text = "MYMAINSCREEN!")
    }
}