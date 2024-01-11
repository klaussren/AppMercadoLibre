package com.example.appmercadolibre.data.network

import com.example.appmercadolibre.data.model.CategoriesModel
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import com.example.appmercadolibre.data.model.ItemDetailModel
import com.example.appmercadolibre.data.model.ItemListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz que define las operaciones de la API de MercadoLibre.
 */
interface ApiService {
    /**
     * Obtiene la lista de categorías desde la API.
     *
     * @return Lista de modelos de categorías ([CategoriesModel]).
     */
    @GET("sites/MLA/categories")
    suspend fun getCategories(): List<CategoriesModel>

    /**
     * Obtiene las categorías hijas asociadas a una categoría específica desde la API.
     *
     * @param categoryId Identificador de la categoría principal.
     * @return Modelo de categorías hijas ([ChildrenCategoriesModel]).
     */
    @GET("/categories/{category_id}")
    suspend fun getSubCategories(
        @Path("category_id") categoryId: String
    ): ChildrenCategoriesModel

    /**
     * Obtiene la lista de ítems asociados a una categoría específica desde la API.
     *
     * @param query Consulta para filtrar los ítems por categoría.
     * @return Respuesta que contiene el modelo de lista de ítems ([ItemListModel]).
     */
    @GET("sites/MLA/search?category=")
    suspend fun getItemsByCategory(
        @Query("category") query: String
    ):  Response<ItemListModel>

    /**
     * Busca ítems por una consulta específica desde la API.
     *
     * @param query Consulta de búsqueda.
     * @return Respuesta que contiene el modelo de lista de ítems ([ItemListModel]).
     */
    @GET("sites/MLA/search?q=")
    suspend fun searchItemsByQuery(
        @Query("q") query: String
    ): Response<ItemListModel>

    /**
     * Obtiene detalles de un ítem específico desde la API.
     *
     * @param itemId Identificador del ítem.
     * @return Respuesta que contiene el modelo de detalle del ítem ([ItemDetailModel]).
     */
    @GET("/items/{item_id}")
    suspend fun getItemDetail(
        @Path("item_id") category: String
    ): Response<ItemDetailModel>


}