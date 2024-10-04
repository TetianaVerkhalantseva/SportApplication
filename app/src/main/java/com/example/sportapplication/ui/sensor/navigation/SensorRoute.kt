package com.example.sportapplication.ui.sensor.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.sportapplication.ui.sensor.SensorScreenRoute

const val SENSOR_ROUTE = "sensor_route"

fun NavController.navigateToSensor(navOptions: NavOptions? = null) =
    navigate(SENSOR_ROUTE, navOptions = navOptions)

fun NavGraphBuilder.sensorScreen(
    navHostController: NavHostController,
) {
    composable(
        route = SENSOR_ROUTE
    ) {
        SensorScreenRoute()
    }
}