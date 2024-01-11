package com.example.appmercadolibre.data.model

import com.google.gson.annotations.SerializedName

/**
 * Clase que representa un modelo detallado de un Item.
 *
 * @property id Identificador único de un Item.
 * @property title Título o nombre de un Item.
 * @property price Precio de un Item.
 * @property pictures Lista de modelos de imágenes asociadas al item.
 * @property attributes Lista de modelos de atributos asociados al item.
 * @property warranty Garantía asociada al item.
 */
data class ItemDetailModel(

    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("pictures")
    val pictures: List<PicturesModel>,
    @SerializedName("attributes")
    val attributes: List<AttributesModel>,
    @SerializedName("warranty")
    val warranty: String

)