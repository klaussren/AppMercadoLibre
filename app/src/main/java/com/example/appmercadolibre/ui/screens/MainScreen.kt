package com.example.appmercadolibre.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appmercadolibre.R
import com.example.appmercadolibre.data.model.ItemsModel
import com.example.appmercadolibre.ui.theme.AppMercadoLibreTheme
import com.example.appmercadolibre.ui.theme.colorTextBarSerach
import com.example.appmercadolibre.ui.theme.primaryColor
import com.example.appmercadolibre.ui.theme.secondaryColor


/**
 * Composable que representa la pantalla principal (Main Screen) de la aplicación.
 *
 * @param navController Controlador de navegación para gestionar la transición entre pantallas.
 * @param searchItemsViewModel ViewModel para la búsqueda de Items.
 * @param categoryShearchViewModel ViewModel para la búsqueda de categorías.
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavController,
    searchItemsViewModel: SearchItemsViewModel,
    categoryShearchViewModel: CategoryShearchViewModel
) {

// Recopilación de estados del ViewModel de búsqueda de artículos
    val searchText by searchItemsViewModel.searchText.collectAsState()
    val isSearching by searchItemsViewModel.isSearching.collectAsState()
    val startSearch by searchItemsViewModel.startSearch.collectAsState()
    val searchResults by searchItemsViewModel.searchResults.collectAsState()
    val showCategory by searchItemsViewModel.showCategory.collectAsState()
    val isConnected by searchItemsViewModel.isConnected.collectAsState()

    // Efecto de lanzamiento para verificar la conectividad al inicio
    LaunchedEffect(key1 = true) {

        searchItemsViewModel.checkConnectivity()
    }

    // Composable principal que define la estructura visual de la pantalla
    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.background
            ) {
                // Barra de búsqueda integrada
                SearchBar(
                    query = searchText,//texto que se muestra SearchBar
                    onQueryChange = { searchItemsViewModel.onSearchTextChange(it) }, // actualizar el valor de la barra con el searchText
                    onSearch = {
                        searchItemsViewModel.searchItemsOnBoard(searchText)
                        if (searchText.equals("")) {
                            searchItemsViewModel.setShowCategory(true)
                        }
                    }, //ejecutar la busqueda
                    active = isSearching, //si la barra de busqueda esta activa
                    onActiveChange = { searchItemsViewModel.onToogleSearch() }, //toggle de la barra de busqueda
                    modifier = Modifier
                        .wrapContentHeight()
                        .background(primaryColor)
                        .padding(16.dp),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.searchText),
                            color = colorTextBarSerach
                        )
                    },
                    leadingIcon = {
                        IconButton(
                            onClick = {
                                searchItemsViewModel.searchItemsOnBoard(searchText)
                            },
                            enabled = searchText.isNotEmpty()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    },
                    trailingIcon = {
                        if (isSearching) {
                            Icon(
                                modifier = Modifier.clickable {
                                    if (searchText.isNotEmpty()) {
                                        searchItemsViewModel.setSearchText("")
                                    } else {
                                        searchItemsViewModel.isSearching(false)
                                        searchItemsViewModel.searchItemsOnBoard("")
                                        searchItemsViewModel.setShowCategory(true)
                                    }
                                },
                                imageVector = Icons.Default.Close,
                                contentDescription = "errase icon"
                            )
                        }
                    }


                ) {
                    // Lista de resultados de búsqueda
                    LazyColumn {
                        itemsIndexed(searchResults.body()?.results.orEmpty()) { index, item ->

                            SearchItemCard(item = item) { searchText ->

                                searchItemsViewModel.onSearchTextChange(searchText)
                                searchItemsViewModel.isSearching(false)
                            }
                        }

                    }
                }
            }
        }

    ) { paddingValues ->
        // Verificar conectividad y mostrar contenido principal
        searchItemsViewModel.checkConnectivity()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            if (startSearch) {
                // Mostrar indicador de carga o mensaje de no conexión según el estado
                if (!isConnected) {
                    NoConnectionMessage()
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center),
                        color = secondaryColor

                    )
                }

            } else {
                // Mostrar contenido principal según los resultados de búsqueda
                MainScreenContent(
                    searchResults = searchResults.body()?.results.orEmpty(),
                    showCategory = showCategory,
                    isConnected = isConnected,
                    modifier = Modifier.padding(paddingValues),
                    categoryShearchViewModel,
                    navController
                )
            }
        }

    }
}

/**
 * Composable que define el contenido principal de la pantalla principal (Main Screen).
 *
 * @param searchResults Lista de resultados de búsqueda.
 * @param showCategory Indica si se debe mostrar la búsqueda por categoría.
 * @param isConnected Indica si hay conexión a Internet.
 * @param modifier Modificador para personalizar la apariencia.
 * @param categoryShearchViewModel ViewModel para la búsqueda de categorías.
 * @param navController Controlador de navegación para gestionar la transición entre pantallas.
 */
@Composable
fun MainScreenContent(
    searchResults: List<ItemsModel>,
    showCategory: Boolean,
    isConnected: Boolean,
    modifier: Modifier = Modifier,
    categoryShearchViewModel: CategoryShearchViewModel,
    navController: NavController
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Mostrar mensaje de no conexión si no hay Internet
        if (!isConnected) {
            NoConnectionMessage()
            return
        }

        // Muestra las categorías solo si no estás buscando y no hay resultados de búsqueda
        if (showCategory && searchResults.isEmpty()) {
            CategorySearchScreen(
                categoryShearchViewModel = categoryShearchViewModel,
                navController = navController
            )
            return
        }

        // Muestra la lista de elementos solo si hay resultados de búsqueda
        if (searchResults.isNotEmpty()) {
            LazyColumn {
                itemsIndexed(searchResults) { index, item ->
                    ItemsCardContent(item = item) { id ->
                        navController.navigate("item_detail_screen/$id")
                    }
                }
            }
        } else {
            // Si se está buscando pero no hay resultados, mostrar un mensaje

            NoResultsMessage()

        }
    }
}


/**
 * Composable que define la apariencia de una tarjeta de elemento de búsqueda.
 *
 * @param item Modelo de artículo para mostrar en la tarjeta.
 * @param onItemClick Función de clic para manejar la acción cuando se hace clic en la tarjeta.
 */
@Composable
fun SearchItemCard(item: ItemsModel, onItemClick: (String) -> Unit) {

    Row(modifier = Modifier.padding(all = 14.dp)) {
        Icon(
            modifier = Modifier.padding(end = 10.dp),
            imageVector = Icons.Default.History,
            contentDescription = "History Icon",
            tint = colorTextBarSerach
        )
        Text(
            text = item.title,
            modifier = Modifier
                .clickable {
                    onItemClick(item.title)
                },
            color = colorTextBarSerach
        )

    }

}



@Composable
private fun PreviewGeneral() {

    AppMercadoLibreTheme {

    }


}

@Preview(name = "NEXUS_5", device = Devices.NEXUS_5X)
@Composable
private fun PreviewCompact() {
    PreviewGeneral()
}
