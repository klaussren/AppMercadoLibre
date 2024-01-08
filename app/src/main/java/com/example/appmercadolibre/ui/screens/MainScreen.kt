package com.example.appmercadolibre.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appmercadolibre.data.model.ItemsModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen (navController: NavController, searchItemsViewModel: SearchItemsViewModel, categoryShearchViewModel: CategoryShearchViewModel) {


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
                modifier = Modifier.padding(paddingValues),
                categoryShearchViewModel,
                navController
            )
    }
}


@Composable
fun MainScreenContent(searchResults: List<ItemsModel>, modifier: Modifier = Modifier, categoryShearchViewModel: CategoryShearchViewModel, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (searchResults.isNotEmpty()) {
            // Si hay resultados de búsqueda, mostrar la lista de elementos
            LazyColumn {
                itemsIndexed(searchResults) { index, item ->
                    SearchItemCardContent(item = item) {id->
                        navController.navigate("item_detail_screen/$id")


                    }
                }
            }
        } else {
           /* Text(
                text = "No se encontraron resultados",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )*/

            CategorySearchScreen(categoryShearchViewModel = categoryShearchViewModel, navController = navController)
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


@Composable
fun SearchItemCardContent(item: ItemsModel, onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onItemClick(item.id)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

                var imageproduct = item.thumbnail.replace("http://", "https://")

            AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageproduct)
                        .crossfade(true)
                        .build(),
                    contentDescription = "image_Product",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)

                )

            // Mostrar el título
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Mostrar el precio
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$ ${item.price}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }
    }
}

