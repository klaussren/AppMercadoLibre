package com.example.appmercadolibre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appmercadolibre.R
import com.example.appmercadolibre.ui.navigation.AppScreens
import kotlinx.coroutines.delay

/**
 * Composable que representa la pantalla de presentación (Splash Screen) de la aplicación.
 *
 * @param navController Controlador de navegación utilizado para la transición a la pantalla principal.
 */
@Composable
fun SplashScreen(navController: NavController) {

    // Efecto lanzado al comienzo de la composición para simular una pausa de 2000 milisegundos (2 segundos).
    LaunchedEffect(key1 = true){
        delay(2000)
        // Se elimina el back stack y se navega a la pantalla principal (Main Screen).
        navController.popBackStack()
        navController.navigate(AppScreens.MainScreen.route)
    }
    // Se llama al composable que representa el diseño visual de la pantalla de presentación.
    Splash()
}

/**
 * Composable que define la apariencia visual de la pantalla de presentación (Splash Screen).
 */
@Composable
fun Splash(){
    // Columna que ocupa toda la pantalla con un fondo amarillo y contenido centrado.
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.Yellow
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Imagen del logotipo de la aplicación con dimensiones específicas.
        Image(
            painter = painterResource(id = R.drawable.icon_meli),
            contentDescription = "",
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
        )


    }
}

/**
 * Vista previa de la pantalla de presentación (Splash Screen) para el entorno de diseño en tiempo de desarrollo.
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    Splash()

}