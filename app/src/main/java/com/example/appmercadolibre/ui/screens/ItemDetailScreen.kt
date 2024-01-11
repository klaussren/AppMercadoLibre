package com.example.appmercadolibre.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appmercadolibre.data.model.AttributesModel
import com.example.appmercadolibre.data.model.ItemDetailModel
import com.example.appmercadolibre.data.model.PicturesModel
import com.example.appmercadolibre.ui.theme.AppMercadoLibreTheme
import com.example.appmercadolibre.ui.theme.primaryColor
import com.example.appmercadolibre.ui.theme.secondaryColor
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

/**
 * Composable que representa la pantalla de detalles del Item.
 *
 * @param navController Controlador de navegación de Compose.
 * @param itemDetailViewModel ViewModel que gestiona la lógica de los detalles del Item.
 */
@Composable
fun ItemDetailScreen(navController: NavController, itemDetailViewModel: ItemDetailViewModel) {

    // Obtener el ID del Item de los argumentos de navegación
    val arguments = navController.currentBackStackEntry?.arguments
    val itemID = arguments?.getString("id") ?: ""
    // Observar el resultado de la búsqueda de detalles del Item y la conectividad a Internet
    val resultItem by itemDetailViewModel.result.collectAsState()
    val isConnected by itemDetailViewModel.isConnected.collectAsState()


    // Efecto de lanzamiento para la inicialización y carga de datos.
    LaunchedEffect(key1 = true) {
        itemDetailViewModel.clearResult()
        itemDetailViewModel.checkConnectivity()
        itemDetailViewModel.getItemDetail(itemID)
    }
    // Verificar la conectividad y mostrar la pantalla correspondiente
    if(isConnected) {

        if (resultItem.isSuccessful) {
            val responseBody = resultItem.body()

            if (responseBody != null) {

                val itemDetailModel: ItemDetailModel = responseBody

                ScreenPortrait(resultItem = itemDetailModel) {
                    navController.popBackStack()

                }

            }
        } else {
            // Manejo de errores o indicador de carga
            NoResultsMessage()
        }
    }
    else {
        // Pantalla sin conexión
        NoConnectionMessage()
    }


}

/**
 * Composable que representa la pantalla de detalles del Item en modo retrato.
 *
 * @param resultItem Detalles del Item.
 * @param onClickBackButton Acción a realizar al hacer clic en el botón de retroceso.
 */
@Composable
fun ScreenPortrait(resultItem: ItemDetailModel, onClickBackButton: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        // Configuración de la barra de navegación
        TopAppBar(
            backgroundColor = primaryColor, // Color de fondo amarillo
            title = { Text(text = "Detalle del Producto") }, // Título de la barra
            navigationIcon = {
                // Botón de retroceso
                IconButton(
                    onClick = {
                        // Navegar hacia atrás al hacer clic en el botón de retroceso
                        onClickBackButton(true)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black // Color del icono
                    )
                }
            }
        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            val (imageSlider, title, price, warranty, productDetailsTitle, attributes) = createRefs()

            // ImageSlider
            ImageSlider(
                images = resultItem.pictures?.map { it.url } ?: emptyList(),
                modifier = Modifier
                    .constrainAs(imageSlider) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            // Title
            Box(
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(imageSlider.bottom, margin = 8.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                    .fillMaxWidth()
            ) {
                Text(
                    text = resultItem.title ?: "",
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Price
            Text(
                text = "Precio: $${resultItem.price ?: 0}",
                modifier = Modifier
                    .constrainAs(price) {
                        top.linkTo(title.bottom, margin = 4.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
            )

            // Warranty
            Text(
                text = "Garantía: ${resultItem.warranty}",
                modifier = Modifier
                    .constrainAs(warranty) {
                        top.linkTo(price.bottom, margin = 4.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
            )

            // Product Details Title
            Text(
                text = "Detalles del producto",
                modifier = Modifier
                    .constrainAs(productDetailsTitle) {
                        top.linkTo(warranty.bottom, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
                    .padding(top = 8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            // Attributes
            Column(
                modifier = Modifier
                    .constrainAs(attributes) {
                        top.linkTo(productDetailsTitle.bottom, margin = 4.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                    }
            ) {
                resultItem.attributes?.forEach { attribute ->
                    Text(
                        text = "${attribute.name}: ${attribute.value_name}",
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }
}

/**
 * Composable que representa un slider de imágenes.
 *
 * @param images Lista de URLs de imágenes.
 * @param modifier Modificador para personalizar la apariencia del slider.
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun ImageSlider(images: List<String>, modifier: Modifier = Modifier) {
// Implementación del slider de imágenes con HorizontalPager
    val pagerState = rememberPagerState(
        initialPage = 0
    ) {
        images.size
    }
    // Cada 2 segundos cambia la imagen
    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2600)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Column(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            modifier = modifier
                .height(300.dp)
                .fillMaxWidth()
        ) { page ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(images[page].replace("http://", "https://"))
                        .crossfade(true)
                        .build(),
                    contentDescription = "image_Product",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                        .padding(end = 8.dp)

                )
            }
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) secondaryColor else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )

            }
        }

    }


}

/**
 * Composable de vista previa para la pantalla de detalles del Item.
 */
@Composable
private fun PreviewGeneral() {

    AppMercadoLibreTheme {
        ScreenPortrait(
            ItemDetailModel(
                id = "1",
                title = "title",
                price = 100.0,
                pictures = listOf(
                    PicturesModel(
                        id = "1",
                        url = "url"
                    )
                ),
                attributes = listOf(
                    AttributesModel(
                        id = "1",
                        name = "name",
                        value_name = "value_name"

                    ),
                    AttributesModel(
                        id = "1",
                        name = "name",
                        value_name = "value_name"

                    ),
                    AttributesModel(
                        id = "1",
                        name = "name",
                        value_name = "value_name"

                    ),
                    AttributesModel(
                        id = "1",
                        name = "name",
                        value_name = "value_name"

                    )
                ),
                warranty = "warranty"

            ),
            onClickBackButton = {}

        )
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