package com.example.appmercadolibre.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appmercadolibre.ui.navigation.AppScreens

@Composable
fun MainScreen (navController: NavController) {

    val scaffoldState = rememberScaffoldState()

  /*  Scaffold(
        topBar = {
            SearchBar(onSearch = { query ->
                if (query.isNotEmpty()) {
                    // Si se realiza una búsqueda, navega a la pantalla de búsqueda de productos
                    navController.navigate(AppScreens.ProductSearchScreen.route)
                }
            })
        },
        scaffoldState = scaffoldState,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Contenido de la pantalla principal
            Text("Contenido de la pantalla principal")

            // Botón para navegar a la pantalla de detalles del producto
            Button(onClick = {
                navController.navigate(AppScreens.ProductDetailsScreen.route)
            }) {
                Text("Ir a los detalles del producto")
            }
        }
    }*/
}

/*
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var query by remember { mutableStateOf("") }

    OutlinedTextField(
        value = query,
        onValueChange = {
            query = it
            onSearch.invoke(it)
        },
        label = { Text("Buscar") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}
*/
