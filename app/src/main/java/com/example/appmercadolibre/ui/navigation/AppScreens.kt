package com.example.appmercadolibre.ui.navigation

/**
 * Clase sellada que representa los destinos de pantalla (screens) de la aplicación.
 *
 * @param route Ruta asociada al destino de pantalla.
 */
sealed class AppScreens(val route:String){

    /**
     * Objeto que representa la pantalla de presentación (Splash Screen).
     */
    object SplashScreen: AppScreens("splash_screen")

    /**
     * Objeto que representa la pantalla principal (Main Screen).
     */
    object MainScreen: AppScreens("main_screen")
    /**
     * Objeto que representa la pantalla de búsqueda por categoría (Category Search Screen).
     */
    object CategorySearchScreen: AppScreens("category_search_screen")

}
