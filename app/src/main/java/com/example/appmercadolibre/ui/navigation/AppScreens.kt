package com.example.appmercadolibre.ui.navigation

sealed class AppScreens(val route:String){

    object SplashScreen: AppScreens("splash_screen")

    object MainScreen: AppScreens("main_screen")
    object ProductSearchScreen: AppScreens("product_search_screen")
    object ProductDetailsScreen:AppScreens("product_details_screen")

}
