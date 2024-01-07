package com.example.appmercadolibre.data.model

import com.google.gson.annotations.SerializedName

data class AttributesModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("value_name")
    val value_name: String

)
