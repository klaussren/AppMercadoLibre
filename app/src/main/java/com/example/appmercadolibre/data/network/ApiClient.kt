package com.example.appmercadolibre.data.network

import com.example.appmercadolibre.data.model.CategoriesModel
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import com.example.appmercadolibre.data.model.ItemDetailModel
import com.example.appmercadolibre.data.model.ItemListModel
import retrofit2.Response
import javax.inject.Inject

/**
 * Clase que proporciona métodos para interactuar con la API de MercadoLibre.
 *
 * @property apiService Instancia de [ApiService] que se utiliza para realizar llamadas a la API.
 */
class ApiClient @Inject constructor(private val apiService: ApiService) {

    /**
     * Obtiene la lista de categorías desde la API.
     *
     * @return Lista de modelos de categorías ([CategoriesModel]).
     */
    suspend fun getCategories():  List<CategoriesModel> {
        return apiService.getCategories()
    }

    /**
     * Obtiene las categoría con un valor mas que es la imagen de una categoría específica desde la API.
     *
     * @param categoryID Identificador de la categoría principal.
     * @return Modelo de categoría con imagen([ChildrenCategoriesModel]).
     */
    suspend fun getChildrenCategories(categoryID: String):  ChildrenCategoriesModel {
        return apiService.getSubCategories(categoryID)
    }

    /**
     * Obtiene la lista de ítems asociados a una categoría específica desde la API.
     *
     * @param itemID Identificador de la categoría o ítem.
     * @return Respuesta que contiene el modelo de lista de ítems ([ItemListModel]).
     */
    suspend fun getItemsByCategory(itemID: String):  Response<ItemListModel> {
        return apiService.getItemsByCategory(itemID)
    }
    /**
     * Busca ítems por una consulta específica desde la API.
     *
     * @param query Consulta de búsqueda.
     * @return Respuesta que contiene el modelo de lista de ítems ([ItemListModel]).
     */
    suspend fun searchItemsByQuery(query: String): Response<ItemListModel> {
        return apiService.searchItemsByQuery(query)
    }

    /**
     * Obtiene detalles de un ítem específico desde la API.
     *
     * @param itemID Identificador del ítem.
     * @return Respuesta que contiene el modelo de detalle del ítem ([ItemDetailModel]).
     */
    suspend fun getItemDetail(itemID: String): Response<ItemDetailModel> {
        return apiService.getItemDetail(itemID)
    }


}