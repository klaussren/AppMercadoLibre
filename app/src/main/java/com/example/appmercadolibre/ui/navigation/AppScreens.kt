package com.example.appmercadolibre.ui.navigation

sealed class AppScreens(val route:String){

    object SplashScreen: AppScreens("splash_screen")

    object MainScreen: AppScreens("main_screen")
    object CategorySearchScreen: AppScreens("category_search_screen")
    object ItemDetailScreen:AppScreens("item_detail_screen")

}
