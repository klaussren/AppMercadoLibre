package com.example.appmercadolibre.data.model

import com.google.gson.annotations.SerializedName

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
