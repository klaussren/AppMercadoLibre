package com.example.appmercadolibre.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.appmercadolibre.ui.navigation.AppScreens
import kotlinx.coroutines.delay

@Composable
fun ProductSearchScreen(navController: NavController){
    LaunchedEffect(key1 = true){
        delay(2000)
        navController.popBackStack()
        navController.navigate(AppScreens.ProductSearchScreen.route)
    }
}