package com.example.appmercadolibre.data.network

import com.example.appmercadolibre.data.model.CategoriesModel
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /*  @GET("sites/MLA/search")
      suspend fun searchItemsByCategory(
          @Query("category") category: String
      ): SearchResponse

      @GET("sites/MLA/search")
      suspend fun searchItemsByQuery(
          @Query("q") query: String
      ): SearchResponse

      @GET("sites/MLA/search")
      suspend fun searchItemsBySeller(
          @Query("seller_id") sellerId: String
      ): SearchResponse

      @GET("sites/MLA/search")
      suspend fun searchItemsBySellerAndCategory(
          @Query("seller_id") sellerId: String,
          @Query("category") category: String
      ): SearchResponse
  */

    @GET("sites/MLA/categories")
    suspend fun getCategories(): List<CategoriesModel>

    @GET("/categories/{category_id}")
    suspend fun getSubCategories(
        @Path("category_id") categoryId: String
    ): ChildrenCategoriesModel



}