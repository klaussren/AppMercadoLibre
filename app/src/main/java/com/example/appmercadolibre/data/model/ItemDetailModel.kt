package com.example.appmercadolibre.data.model

import com.google.gson.annotations.SerializedName

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