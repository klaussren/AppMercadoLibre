package com.example.appmercadolibre.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appmercadolibre.ui.screens.ProductDetailsScreen
import com.example.appmercadolibre.ui.screens.ProductSearchScreen
import com.example.appmercadolibre.ui.screens.SplashScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {
        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(AppScreens.ProductDetailsScreen.route) {
            ProductSearchScreen(navController)
        }
        composable(AppScreens.ProductDetailsScreen.route) {
            ProductDetailsScreen(navController)
        }
    }
}