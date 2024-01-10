package com.example.appmercadolibre.ui.screens

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appmercadolibre.R
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import com.example.appmercadolibre.data.model.ItemsModel
import com.example.appmercadolibre.ui.theme.AppMercadoLibreTheme
import com.example.appmercadolibre.ui.theme.colorProgressBar
import com.example.appmercadolibre.ui.theme.secondaryColor


@Composable
fun CategorySearchScreen(categoryShearchViewModel: CategoryShearchViewModel, navController: NavController){

    val childrenCategories by categoryShearchViewModel.childrenCategories.collectAsState(emptyList())
    val isSearching by categoryShearchViewModel.isSearching.collectAsState()
    val itemsResults by categoryShearchViewModel.itemsResults.collectAsState()
    val isSearchingItems by categoryShearchViewModel.isSearchingItems.collectAsState()

    LaunchedEffect(key1 = true) {
        categoryShearchViewModel.clearChildrenCategories()
        categoryShearchViewModel.fetchChildrenCategories()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        if (isSearching) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center),
                color = secondaryColor
            )
        } else {
            // Mostrar la lista de artículos o categorías según el estado de la búsqueda
            val itemsCategoryResult = itemsResults.body()?.results.orEmpty()
            if (itemsCategoryResult.isNotEmpty() && isSearchingItems) {


                LazyColumn {
                    itemsIndexed(itemsCategoryResult) { index, item ->
                        ItemsCardContent(item = item) { id ->
                            navController.navigate("item_detail_screen/$id")
                            categoryShearchViewModel.setIsSearchingItems(false)

                        }
                    }
                }

            } else {
                // Mostrar la lista de categorías si no hay resultados y no está buscando
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                        // Lista de categorías
                        ScreenPortrait(childrenCategories = childrenCategories,
                            onCategoryClick = { id ->
                                // Clic en una categoría
                                categoryShearchViewModel.getItemsByCategory(id)
                                categoryShearchViewModel.clearChildrenCategories()
                            }
                        )


                }
            }
        }
    }
}


@Composable
fun ScreenPortrait(childrenCategories: List<ChildrenCategoriesModel>, onCategoryClick: (String) -> Unit) {

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.categoriesText),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )
        ChildrenCategoriesList(childrenCategories, onCategoryClick = {onCategoryClick(it)})
    }

}


@Composable
fun ChildrenCategoriesList(childrenCategories: List<ChildrenCategoriesModel>, onCategoryClick: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(childrenCategories) { childrenCategory ->
            ChildrenCategoryItem(childrenCategories = childrenCategory){ id->
                // Clic en una categoría
                onCategoryClick(id)

            }
        }
    }
}

@Composable
fun ChildrenCategoryItem(childrenCategories: ChildrenCategoriesModel, onCategoryClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(8.dp)
            .clickable { onCategoryClick(childrenCategories.id) },
        elevation = 4.dp
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {

            val (imageCategory,textCategory )= createRefs()



            // Mostrar la imagen con CoilImage centrada y del mismo tamaño para todos los elementos
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
            // Mostrar el nombre debajo de la imagen con un máximo de 2 líneas
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




@Composable
private fun PreviewGeneral() {


    AppMercadoLibreTheme {
        ScreenPortrait(
            listOf(
                ChildrenCategoriesModel("1", "Accesorios para Vehículos",""),
                ChildrenCategoriesModel("2", "Alimentos y Bebidas",""),
                ChildrenCategoriesModel("3", "Antigüedades y Colecciones","")

            ),
            onCategoryClick = {})
    }


}

@Preview(name = "NEXUS_5", device = Devices.NEXUS_5X)
@Composable
private fun PreviewCompact() {
    PreviewGeneral()
}