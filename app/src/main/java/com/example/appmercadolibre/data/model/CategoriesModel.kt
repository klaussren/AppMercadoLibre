package com.example.appmercadolibre.data.model

import com.google.gson.annotations.SerializedName
/**
 * Clase que representa un modelo de Categorias.
 *
 * @property id Identificador único de la categoría.
 * @property name Nombre de la categoría.
 */
data class CategoriesModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)
