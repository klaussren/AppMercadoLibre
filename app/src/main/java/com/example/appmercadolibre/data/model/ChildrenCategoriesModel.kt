package com.example.appmercadolibre.data.model

import com.google.gson.annotations.SerializedName
/**
 * Clase que representa un modelo de categorías con su imagen.
 *
 * @property id Identificador único de la categoría.
 * @property name Nombre de la categoría.
 * @property picture URL de la imagen asociada a la categoría.
 */
data class ChildrenCategoriesModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("picture")
    val picture: String
)
