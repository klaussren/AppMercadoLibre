package com.example.appmercadolibre.data

import com.example.appmercadolibre.data.model.CategoriesModel
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import com.example.appmercadolibre.data.model.ItemDetailModel
import com.example.appmercadolibre.data.model.ItemListModel
import com.example.appmercadolibre.data.network.ApiClient
import retrofit2.Response
import javax.inject.Inject

/**
 * Clase que actúa como un repositorio para interactuar con la API de MercadoLibre a través del [ApiClient].
 *
 * @property apiClient Instancia de [ApiClient] que se utiliza para realizar llamadas a la API.
 */
class MeliRepository @Inject constructor(private val apiClient: ApiClient) {

    /**
     * Obtiene la lista de categorías desde la API.
     *
     * @return Lista de modelos de categorías ([CategoriesModel]).
     */
    suspend fun getCategories(): List<CategoriesModel> {
        return apiClient.getCategories()
    }

    /**
     *  Obtiene las categoría con un valor mas que es la imagen de una categoría específica desde la API.
     *
     * @param categoryID Identificador de la categoría principal.
     * @return Modelo de categorías con imagen ([ChildrenCategoriesModel]).
     */
    suspend fun getChildrenCategories(categoryID: String): ChildrenCategoriesModel {
        return apiClient.getChildrenCategories(categoryID)
    }

    /**
     * Obtiene la lista de ítems asociados a una categoría específica desde la API.
     *
     * @param itemID Identificador de la categoría o ítem.
     * @return Respuesta que contiene el modelo de lista de ítems ([ItemListModel]).
     */
    suspend fun getItemsByCategory(itemID: String): Response<ItemListModel> {
        return apiClient.getItemsByCategory(itemID)
    }

    /**
     * Busca ítems por una consulta específica desde la API.
     *
     * @param query Consulta de búsqueda.
     * @return Respuesta que contiene el modelo de lista de ítems ([ItemListModel]).
     */
    suspend fun searchItemsByQuery(query: String): Response<ItemListModel> {
        return apiClient.searchItemsByQuery(query)
    }

    /**
     * Obtiene detalles de un ítem específico desde la API.
     *
     * @param itemID Identificador del ítem.
     * @return Respuesta que contiene el modelo de detalle del ítem ([ItemDetailModel]).
     */
    suspend fun getItemDetail(itemID: String): Response<ItemDetailModel> {
        return apiClient.getItemDetail(itemID)
    }

}