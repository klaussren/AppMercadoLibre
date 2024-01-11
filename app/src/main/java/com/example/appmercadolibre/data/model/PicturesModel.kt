package com.example.appmercadolibre.data.model

import com.google.gson.annotations.SerializedName

/**
 * Clase que representa un modelo de imágenes asociadas a un artículo.
 *
 * @property id Identificador único de la imagen.
 * @property url URL de la imagen.
 */
data class PicturesModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String
)
