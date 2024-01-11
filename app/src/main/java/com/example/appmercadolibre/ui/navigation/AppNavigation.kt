package com.example.appmercadolibre.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appmercadolibre.ui.screens.CategorySearchScreen
import com.example.appmercadolibre.ui.screens.CategoryShearchViewModel
import com.example.appmercadolibre.ui.screens.ItemDetailScreen
import com.example.appmercadolibre.ui.screens.ItemDetailViewModel
import com.example.appmercadolibre.ui.screens.MainScreen
import com.example.appmercadolibre.ui.screens.SearchItemsViewModel
import com.example.appmercadolibre.ui.screens.SplashScreen

/**
 * Función que define la navegación de la aplicación utilizando Jetpack Compose y el componente Navigation.
 *
 * @param searchItemsViewModel ViewModel para la búsqueda de Items.
 * @param categoryShearchViewModel ViewModel para la búsqueda de categorías.
 * @param itemDetailViewModel ViewModel para los detalles del Items.
 */
@Composable
fun AppNavigation(searchItemsViewModel: SearchItemsViewModel,categoryShearchViewModel: CategoryShearchViewModel, itemDetailViewModel: ItemDetailViewModel) {

    // Se crea un controlador de navegación para gestionar la navegación entre destinos.
    val navController = rememberNavController()

    // Se define el conjunto de destinos y sus respectivas composiciones.
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {

        // Pantalla de presentación (Splash Screen)
        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }
        // Pantalla principal (Main Screen)
        composable(AppScreens.MainScreen.route) {
            MainScreen(navController,searchItemsViewModel,categoryShearchViewModel)
        }
        // Pantalla de búsqueda por categoría (Category Search Screen)
        composable(AppScreens.CategorySearchScreen.route) {
            CategorySearchScreen(categoryShearchViewModel,navController)
        }
        // Pantalla de detalles del artículo (Item Detail Screen)
        composable(
            route= "item_detail_screen/{id}",
            arguments = listOf(
                navArgument("id") {type = NavType.StringType}
            )
        ) {backstackEntry->
            val id = backstackEntry.arguments?.getString("id") ?: ""
            requireNotNull(id)
            ItemDetailScreen(navController,itemDetailViewModel)
        }
    }
}