package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.ItemListModel
import com.example.appmercadolibre.data.model.ItemsModel
import retrofit2.Response
import javax.inject.Inject

class SearchItemsUseCase@Inject constructor(private val meliRepository: MeliRepository) {

    suspend operator fun invoke(query: String): Response<ItemListModel> {
        return meliRepository.searchItemsByQuery(query)
    }
}