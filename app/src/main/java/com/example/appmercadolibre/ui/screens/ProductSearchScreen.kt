package com.example.appmercadolibre.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import com.example.appmercadolibre.ui.theme.AppMercadoLibreTheme


@Composable
fun ProductSearchScreen(productShearchViewModel: ProductShearchViewModel,navController: NavController){


    val childrenCategories by productShearchViewModel.childrenCategories.collectAsState(emptyList())

    LaunchedEffect(key1 = true) {

        productShearchViewModel.fetchChildrenCategories()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Lista de categorías
       ScreenPortrait(childrenCategories = childrenCategories)


       /* CategoryList(categories) { selectedCategory ->
            productShearchViewModel.fetchDataByCategory(selectedCategory.id)
        }*/

    }

}

@Composable
fun ScreenPortrait(childrenCategories: List<ChildrenCategoriesModel>) {

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        ChildrenCategoriesList(childrenCategories)
    }

}


@Composable
fun ChildrenCategoriesList(childrenCategories: List<ChildrenCategoriesModel>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(childrenCategories) { childrenCategory ->
            ChildrenCategoryItem(childrenCategories = childrenCategory)
        }
    }
}

@Composable
fun ChildrenCategoryItem(childrenCategories: ChildrenCategoriesModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(8.dp),
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

            ))
    }


}

@Preview(name = "NEXUS_5", device = Devices.NEXUS_5X)
@Composable
private fun PreviewCompact() {
    PreviewGeneral()
}