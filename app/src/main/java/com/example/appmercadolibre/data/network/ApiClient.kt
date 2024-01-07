package com.example.appmercadolibre.data.network

import com.example.appmercadolibre.data.model.CategoriesModel
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import com.example.appmercadolibre.data.model.ItemDetailModel
import com.example.appmercadolibre.data.model.ItemListModel
import com.example.appmercadolibre.data.model.ItemsModel
import retrofit2.Response
import javax.inject.Inject

class ApiClient @Inject constructor(private val apiService: ApiService) {

    suspend fun getCategories():  List<CategoriesModel> {
        return apiService.getCategories()
    }


    suspend fun getChildrenCategories(categoryID: String):  ChildrenCategoriesModel {
        return apiService.getSubCategories(categoryID)
    }

    suspend fun searchItemsByQuery(query: String): Response<ItemListModel> {
        return apiService.searchItemsByQuery(query)
    }


    suspend fun getItemDetail(itemID: String): Response<ItemDetailModel> {
        return apiService.getItemDetail(itemID)
    }


}