package com.example.appmercadolibre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmercadolibre.R

/**
 * Composable que muestra un mensaje cuando no hay conexión a Internet.
 */
@Composable
fun NoConnectionMessage() {
    // Columna que ocupa toda la pantalla con contenido centrado vertical y horizontalmente.
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Imagen que representa la falta de conexión.
        Image(
            painter = painterResource(id = R.drawable.no_connection),
            contentDescription = "",
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
        )
        // Espaciador vertical para separar la imagen del texto.
        Spacer(modifier = Modifier.height(8.dp))
        // Texto que informa al usuario sobre la falta de conexión.
        Text(
            text = stringResource(id = R.string.noConnectionMessage),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
