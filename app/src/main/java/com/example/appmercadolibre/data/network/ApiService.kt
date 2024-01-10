package com.example.appmercadolibre.data.network

import com.example.appmercadolibre.data.model.CategoriesModel
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import com.example.appmercadolibre.data.model.ItemDetailModel
import com.example.appmercadolibre.data.model.ItemListModel
import com.example.appmercadolibre.data.model.ItemsModel
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("sites/MLA/categories")
    suspend fun getCategories(): List<CategoriesModel>

    @GET("/categories/{category_id}")
    suspend fun getSubCategories(
        @Path("category_id") categoryId: String
    ): ChildrenCategoriesModel

    @GET("sites/MLA/search?category=")
    suspend fun getItemsByCategory(
        @Query("category") query: String
    ):  Response<ItemListModel>


    @GET("sites/MLA/search?q=")
    suspend fun searchItemsByQuery(
        @Query("q") query: String
    ): Response<ItemListModel>

    @GET("/items/{item_id}")
    suspend fun getItemDetail(
        @Path("item_id") category: String
    ): Response<ItemDetailModel>


}