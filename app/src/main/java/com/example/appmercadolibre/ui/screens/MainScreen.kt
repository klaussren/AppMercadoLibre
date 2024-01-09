package com.example.appmercadolibre.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appmercadolibre.R
import com.example.appmercadolibre.data.model.AttributesModel
import com.example.appmercadolibre.data.model.ItemDetailModel
import com.example.appmercadolibre.data.model.ItemListModel
import com.example.appmercadolibre.data.model.ItemsModel
import com.example.appmercadolibre.data.model.PicturesModel
import com.example.appmercadolibre.ui.theme.AppMercadoLibreTheme
import com.example.appmercadolibre.ui.theme.colorProgressBar
import com.example.appmercadolibre.ui.theme.colorTextBarSerach
import com.example.appmercadolibre.ui.theme.primaryColor
import com.example.appmercadolibre.ui.theme.secondaryColor
import retrofit2.Response


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavController,
    searchItemsViewModel: SearchItemsViewModel,
    categoryShearchViewModel: CategoryShearchViewModel
) {


    val searchText by searchItemsViewModel.searchText.collectAsState()
    val isSearching by searchItemsViewModel.isSearching.collectAsState()
    val startSearch by searchItemsViewModel.startSearch.collectAsState()
    val searchResults by searchItemsViewModel.searchResults.collectAsState()
    val showCategory by searchItemsViewModel.showCategory.collectAsState()
    val isConnected by searchItemsViewModel.isConnected.collectAsState()

    LaunchedEffect(key1 = true) {

        searchItemsViewModel.checkConnectivity()
    }



    ScreenPortrait(searchText, isSearching, startSearch, searchResults)

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.background
            ) {
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

    ) {

            paddingValues ->
        searchItemsViewModel.checkConnectivity()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            if (startSearch) {
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


@Composable
fun ScreenPortrait(
    searchText: String,
    isSearching: Boolean,
    startSearch: Boolean,
    searchResults: Response<ItemListModel>
) {


}


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
        // Si no hay conexión a Internet, mostrar un mensaje de marca de agua
        if (!isConnected) {
            NoConnectionMessage()
            return
        }

        // Muestra las categorías solo si no estás buscando y no hay resultados de búsqueda
        if (showCategory && searchResults.isEmpty()) {
            Text(
                text = stringResource(id = R.string.categoriesText),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            )
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
                    SearchItemCardContent(item = item) { id ->
                        navController.navigate("item_detail_screen/$id")
                    }
                }
            }
        } else {
            // Si estás buscando pero no hay resultados, mostrar mensaje de marca de agua

            NoResultsMessage()

        }
    }
}

@Composable
fun NoConnectionMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ico_no_found),
            contentDescription = "",
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.noConnectionMessage),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NoResultsMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ico_no_found),
            contentDescription = "",
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.noResultsMessage),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SearchItemCard(item: ItemsModel, onItemClick: (String) -> Unit) {

    Row(modifier = Modifier.padding(all = 14.dp)) {
        Icon(
            modifier = Modifier.padding(end = 10.dp),
            imageVector = Icons.Default.History,
            contentDescription = "History Icon",
        )
        Text(
            text = item.title,
            modifier = Modifier
                .clickable {
                    onItemClick(item.title)
                }
        )

    }

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


@Composable
private fun PreviewGeneral() {

    AppMercadoLibreTheme {
        /* ScreenPortrait(
             searchText = "",
             isSearching = false,
             startSearch = false

         )*/
    }


}

@Preview(name = "NEXUS_5", device = Devices.NEXUS_5X)
@Composable
private fun PreviewCompact() {
    PreviewGeneral()
}
