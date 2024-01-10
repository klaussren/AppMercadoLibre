package com.example.appmercadolibre.ui.screens

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appmercadolibre.data.model.ItemsModel
import com.example.appmercadolibre.ui.theme.AppMercadoLibreTheme

@Composable
fun ItemsCardContent(item: ItemsModel, onItemClick: (String) -> Unit) {


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
        ItemsCardContent(
            item = ItemsModel(
                id = "1",
                title = "title",
                price = 100.0,
                thumbnail = "thumbnail"
            ),
            onItemClick = {}
        )
    }
}

@Preview(name = "NEXUS_5", device = Devices.NEXUS_5X)
@Composable
private fun PreviewCompact() {
    PreviewGeneral()
}