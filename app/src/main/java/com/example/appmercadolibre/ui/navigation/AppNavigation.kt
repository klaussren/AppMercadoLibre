package com.example.appmercadolibre.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appmercadolibre.ui.screens.CategorySearchScreen
import com.example.appmercadolibre.ui.screens.MainScreen
import com.example.appmercadolibre.ui.screens.CategoryShearchViewModel
import com.example.appmercadolibre.ui.screens.ItemDetailScreen
import com.example.appmercadolibre.ui.screens.ItemDetailViewModel
import com.example.appmercadolibre.ui.screens.SearchItemsViewModel
import com.example.appmercadolibre.ui.screens.SplashScreen

@Composable
fun AppNavigation(searchItemsViewModel: SearchItemsViewModel,categoryShearchViewModel: CategoryShearchViewModel, itemDetailViewModel: ItemDetailViewModel) {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {

        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(AppScreens.MainScreen.route) {
            MainScreen(navController,searchItemsViewModel,categoryShearchViewModel)
        }
        composable(AppScreens.CategorySearchScreen.route) {
            CategorySearchScreen(categoryShearchViewModel,navController)
        }
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