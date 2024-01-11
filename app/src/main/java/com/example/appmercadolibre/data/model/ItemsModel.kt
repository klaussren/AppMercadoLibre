package com.example.appmercadolibre.data.model

import com.google.gson.annotations.SerializedName

/**
 * Clase que representa un modelo de ítem.
 *
 * @property id Identificador único del ítem.
 * @property title Título o nombre del ítem.
 * @property thumbnail URL de la imagen en miniatura asociada al ítem.
 * @property price Precio del ítem.
 */
data class ItemsModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("price")
    val price: Double
)
