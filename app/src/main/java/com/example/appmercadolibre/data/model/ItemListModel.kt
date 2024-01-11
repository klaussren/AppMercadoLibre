package com.example.appmercadolibre.data.model

import com.google.gson.annotations.SerializedName

/**
 * Clase que representa un modelo de lista de ítems.
 *
 * @property results Lista de modelos de ítems.
 */
class ItemListModel(
    @SerializedName("results") val results: List<ItemsModel>)