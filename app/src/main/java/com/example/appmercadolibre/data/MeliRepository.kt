package com.example.appmercadolibre.data

import com.example.appmercadolibre.data.model.CategoriesModel
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import com.example.appmercadolibre.data.network.ApiClient
import javax.inject.Inject

class MeliRepository @Inject constructor(private val apiClient: ApiClient) {


    suspend fun getCategories(): List<CategoriesModel> {
        return apiClient.getCategories()
    }

    suspend fun getChildrenCategories(categoryID: String): ChildrenCategoriesModel {
        return apiClient.getChildrenCategories(categoryID)
    }

}