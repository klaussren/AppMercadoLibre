package com.example.appmercadolibre.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appmercadolibre.data.model.ItemsModel
import com.example.appmercadolibre.ui.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen (navController: NavController,searchItemsViewModel: SearchItemsViewModel) {


    val searchText by searchItemsViewModel.searchText.collectAsState()
    val isSearching by searchItemsViewModel.isSearching.collectAsState()
    val searchResults by searchItemsViewModel.searchResults.collectAsState()


    Scaffold(
        topBar = {
            SearchBar(
                query = searchText,//texto que se muestra SearchBar
                onQueryChange = { searchItemsViewModel.onSearchTextChange(it) }, // actualizar el valor de la barra con el searchText
                onSearch = { searchText -> searchItemsViewModel.searchItemsOnBoard(searchText)  } , //ejecutar la busqueda
                active = isSearching, //si la barra de busqueda esta activa
                onActiveChange = { searchItemsViewModel.onToogleSearch() }, //toggle de la barra de busqueda
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

               LazyColumn {
                    itemsIndexed(searchResults.body()?.results.orEmpty()) { index, item ->

                        SearchItemCard(item = item) { searchText ->

                            searchItemsViewModel.searchItems(searchText)
                            searchItemsViewModel.onSearchTextChange(searchText)
                            searchItemsViewModel.isSearching(false)
                        }
                    }

                }
            }
        }
    ) {
         paddingValues ->
            MainScreenContent(
                searchResults = searchResults.body()?.results.orEmpty(),
                modifier = Modifier.padding(paddingValues)
            )
    }
}


@Composable
fun MainScreenContent(searchResults: List<ItemsModel>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (searchResults.isNotEmpty()) {
            // Si hay resultados de búsqueda, mostrar la lista de elementos
            LazyColumn {
                itemsIndexed(searchResults) { index, item ->
                    SearchItemCard(item = item) {

                    }
                }
            }
        } else {
            // Si no hay resultados de búsqueda, mostrar otro contenido
            Text(
                text = "No se encontraron resultados",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun SearchItemCard(item: ItemsModel,onItemClick: (String) -> Unit) {

    Text(
        text = item.title,
        modifier = Modifier
            .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
            .clickable {

                onItemClick(item.title)

            }
    )
}

