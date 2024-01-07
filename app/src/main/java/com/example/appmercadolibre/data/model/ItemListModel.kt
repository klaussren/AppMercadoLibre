package com.example.appmercadolibre.data.model

import com.google.gson.annotations.SerializedName

class ItemListModel(
    @SerializedName("results") val results: List<ItemsModel>)