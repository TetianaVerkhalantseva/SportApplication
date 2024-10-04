package com.example.sportapplication.ui.model

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

data class BottomNavItem(
    @StringRes val name : Int,
    @DrawableRes val icon: Int ,
    val route: String
    ) {


}