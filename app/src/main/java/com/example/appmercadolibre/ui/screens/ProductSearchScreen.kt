package com.example.appmercadolibre.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appmercadolibre.data.model.CategoriesModel
import com.example.appmercadolibre.ui.theme.AppMercadoLibreTheme


@Composable
fun ProductSearchScreen(productShearchViewModel: ProductShearchViewModel,navController: NavController){

    val categories by productShearchViewModel.categories.collectAsState(emptyList())

    LaunchedEffect(key1 = true) {
        productShearchViewModel.fetchCategories()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Lista de categorías
       ScreenPortrait(categories = categories)


       /* CategoryList(categories) { selectedCategory ->
            productShearchViewModel.fetchDataByCategory(selectedCategory.id)
        }*/

    }

}

@Composable
fun ScreenPortrait(categories: List<CategoriesModel>) {
    CategoryList(categories)

}



@Composable
fun CategoryList(categories: List<CategoriesModel>) {
    LazyColumn {
        items(categories) { category ->
            CategoryItem(category = category)
        }
    }
}

@Composable
fun CategoryItem(category: CategoriesModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = category.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}




@Composable
private fun PreviewGeneral() {


    AppMercadoLibreTheme {
        ScreenPortrait(
            listOf(
                CategoriesModel("1", "Accesorios para Vehículos"),
                CategoriesModel("2", "Alimentos y Bebidas"),
                CategoriesModel("3", "Antigüedades y Colecciones")

            ))
    }


}

@Preview(name = "NEXUS_5", device = Devices.NEXUS_5X)
@Composable
private fun PreviewCompact() {
    PreviewGeneral()
}