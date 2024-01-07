package com.example.appmercadolibre.data.model

import com.google.gson.annotations.SerializedName

data class PicturesModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String
)
