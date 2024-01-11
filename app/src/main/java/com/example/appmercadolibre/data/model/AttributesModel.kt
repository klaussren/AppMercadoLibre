package com.example.appmercadolibre.data.model

import com.google.gson.annotations.SerializedName

/**
 * Clase que representa un modelo de atributos.
 *
 * @property id Identificador único del atributo.
 * @property name Nombre del atributo.
 * @property value_name Valor del atributo.
 */

data class AttributesModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("value_name")
    val value_name: String

)
