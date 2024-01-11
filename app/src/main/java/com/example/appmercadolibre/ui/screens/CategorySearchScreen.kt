package com.example.appmercadolibre.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appmercadolibre.R
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import com.example.appmercadolibre.ui.theme.AppMercadoLibreTheme
import com.example.appmercadolibre.ui.theme.secondaryColor


/**
 * Composable que representa la pantalla de búsqueda por categoría.
 *
 * @param categoryShearchViewModel ViewModel que gestiona la lógica de búsqueda por categoría.
 * @param navController Controlador de navegación para manejar las transiciones entre pantallas.
 */
@Composable
fun CategorySearchScreen(categoryShearchViewModel: CategoryShearchViewModel, navController: NavController) {

    // Estado de las categorías de la categoría seleccionada.
    val childrenCategories by categoryShearchViewModel.childrenCategories.collectAsState(emptyList())
    // Estado que indica si la búsqueda de categorías está en curso.
    val isSearching by categoryShearchViewModel.isSearching.collectAsState()
    // Resultados de la búsqueda de artículos por categoría.
    val itemsResults by categoryShearchViewModel.itemsResults.collectAsState()
    // Estado que indica si la búsqueda de Items está en curso.
    val isSearchingItems by categoryShearchViewModel.isSearchingItems.collectAsState()
    // Estado que indica si hay conexión a Internet.
    val isConnected by categoryShearchViewModel.isConnected.collectAsState()
    // Nombre de la categoría actualmente seleccionada.
    var nameCategory by rememberSaveable {
        mutableStateOf("")
    }
    // Efecto de lanzamiento para la inicialización y carga de datos.
    LaunchedEffect(key1 = true) {
        categoryShearchViewModel.clearChildrenCategories()
        categoryShearchViewModel.checkConnectivity()
        categoryShearchViewModel.fetchChildrenCategories()
    }
    // Comprobación de la conectividad antes de renderizar el contenido.
    if (isConnected) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        if (isSearching) {
            // Muestra un indicador de progreso durante la búsqueda de categorías.
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center),
                color = secondaryColor
            )
        } else {
            // Verifica la conectividad antes de renderizar el contenido principal.
            categoryShearchViewModel.checkConnectivity()
            // Mostrar la lista de items segun categoria
            val itemsCategoryResult = itemsResults.body()?.results.orEmpty()

            if(isConnected){
            if (itemsCategoryResult.isNotEmpty() && isSearchingItems) {
                Column {
                    Row() {
                        // Botón de retroceso para regresar a la pantalla de categoriras.
                        IconButton(modifier = Modifier.padding(end = 8.dp),
                            onClick = {

                                navController.navigate("main_screen")

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black // Color del icono
                            )
                        }
                        // Muestra el nombre de la categoría actualmente seleccionada.
                        Text(
                            text = nameCategory,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp, top = 8.dp)

                        )

                    }
                    // Lista de Items de la categoría seleccionada.
                    LazyColumn {
                        itemsIndexed(itemsCategoryResult) { index, item ->
                            // Renderiza cada elemento de la lista de artículos.
                            ItemsCardContent(item = item) { id ->
                                // Navega a la pantalla de detalle del Item.
                                navController.navigate("item_detail_screen/$id")
                                categoryShearchViewModel.setIsSearchingItems(false)

                            }
                        }
                    }

                }

            } else {
                // Mostrar la lista de categorías si no hay resultados y no está buscando
                categoryShearchViewModel.checkConnectivity()
                if(isConnected){
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Muestra un texto indicando que se trata de categorías.
                        Text(
                            text = stringResource(id = R.string.categoriesText),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                        )
                        // Lista de categorías
                        ScreenPortrait(childrenCategories = childrenCategories,nameCategory = {nameCategory = it},
                            onCategoryClick = { id ->
                                // Clic en una categoría
                                categoryShearchViewModel.getItemsByCategory(id)
                                categoryShearchViewModel.clearChildrenCategories()
                            }
                        )


                    }
                }
                else{
                    // Muestra un mensaje de falta de conexión si no hay conexión a Internet.
                    NoConnectionMessage()
                }

            }

        }
            else{
                // Muestra un mensaje de falta de conexión si no hay conexión a Internet.
                NoConnectionMessage()
            }
        }
    }
}
    else {
        // Muestra un mensaje de falta de conexión si no hay conexión a Internet.
        NoConnectionMessage()

    }
}


/**
 * Composable que representa la pantalla en orientación vertical.
 *
 * @param childrenCategories Lista de categorías a mostrar.
 * @param nameCategory Función para actualizar el nombre de la categoría actualmente seleccionada.
 * @param onCategoryClick Función que se ejecuta al hacer clic en una categoría.
 */
@Composable
fun ScreenPortrait(childrenCategories: List<ChildrenCategoriesModel>,nameCategory: (String) -> Unit, onCategoryClick: (String) -> Unit) {

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {

        // Muestra la lista de categorias con su imagen.
        ChildrenCategoriesList(
            childrenCategories,
            nameCategory = {nameCategory(it)},
            onCategoryClick = {onCategoryClick(it)})
    }

}


/**
 * Composable que muestra una lista de categorías en una cuadrícula vertical.
 *
 * @param childrenCategories Lista de categorías a mostrar.
 * @param nameCategory Función para actualizar el nombre de la categoría actualmente seleccionada.
 * @param onCategoryClick Función que se ejecuta al hacer clic en una categoría.
 */
@Composable
fun ChildrenCategoriesList(childrenCategories: List<ChildrenCategoriesModel>,nameCategory:(String)->Unit, onCategoryClick: (String) -> Unit) {
    // Cuadrícula vertical que muestra las categorías.
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(childrenCategories) { childrenCategory ->
            ChildrenCategoryItem(childrenCategories = childrenCategory){ id->
                // Clic en una categoría
                onCategoryClick(id)
                nameCategory (childrenCategory.name)

            }
        }
    }
}

/**
 * Composable que representa un elemento individual en la lista de categorías.
 *
 * @param childrenCategories Modelo que contiene información sobre la categoría.
 * @param onCategoryClick Función que se ejecuta al hacer clic en la categoría.
 */
@Composable
fun ChildrenCategoryItem(childrenCategories: ChildrenCategoriesModel, onCategoryClick: (String) -> Unit) {
    // Tarjeta que contiene la información de la categoría.
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(8.dp)
            .clickable { onCategoryClick(childrenCategories.id) },
        elevation = 4.dp
    ) {
        // Diseño de restricciones para posicionar la imagen y el texto.
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (imageCategory,textCategory )= createRefs()
            // Muestra la imagen de la categoría.
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(childrenCategories.picture)
                    .crossfade(true)
                    .build(),
                contentDescription = "image_category",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(imageCategory) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(parent.start, margin = 8.dp)
                        end.linkTo(parent.end, margin = 8.dp)
                    }
                    .fillMaxWidth()
                    .height(120.dp) // Ajusta el tamaño de la imagen
                    .clip(shape = CircleShape)
            )
            //   // Muestra el nombre de la categoría debajo de la imagen.
            Text(
                text = "${childrenCategories.name}",
                fontSize = 14.sp, // Ajusta el tamaño del texto según tus preferencias
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(textCategory) {
                        top.linkTo(imageCategory.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                    }
                    .padding(8.dp) // Ajusta el espacio entre la imagen y el texto
            )

        }
    }
}



/**
 * Composable de vista previa para la pantalla principal.
 */
@Composable
private fun PreviewGeneral() {


    AppMercadoLibreTheme {
        ScreenPortrait(
            listOf(
                ChildrenCategoriesModel("1", "Accesorios para Vehículos",""),
                ChildrenCategoriesModel("2", "Alimentos y Bebidas",""),
                ChildrenCategoriesModel("3", "Antigüedades y Colecciones","")

            ),
            nameCategory = {},
            onCategoryClick = {})
    }


}
/**
 * Composable de vista previa para dispositivos Nexus 5X.
 */
@Preview(name = "NEXUS_5", device = Devices.NEXUS_5X)
@Composable
private fun PreviewCompact() {
    PreviewGeneral()
}