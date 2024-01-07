package com.example.appmercadolibre.data

import com.example.appmercadolibre.data.model.CategoriesModel
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import com.example.appmercadolibre.data.model.ItemDetailModel
import com.example.appmercadolibre.data.model.ItemListModel
import com.example.appmercadolibre.data.model.ItemsModel
import com.example.appmercadolibre.data.network.ApiClient
import retrofit2.Response
import javax.inject.Inject

class MeliRepository @Inject constructor(private val apiClient: ApiClient) {


    suspend fun getCategories(): List<CategoriesModel> {
        return apiClient.getCategories()
    }

    suspend fun getChildrenCategories(categoryID: String): ChildrenCategoriesModel {
        return apiClient.getChildrenCategories(categoryID)
    }

    suspend fun searchItemsByQuery(query: String): Response<ItemListModel> {
        return apiClient.searchItemsByQuery(query)
    }

    suspend fun getItemDetail(itemID: String): Response<ItemDetailModel> {
        return apiClient.getItemDetail(itemID)
    }

}