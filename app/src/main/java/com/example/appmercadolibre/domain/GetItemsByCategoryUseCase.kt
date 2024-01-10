package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.ItemListModel
import retrofit2.Response
import javax.inject.Inject

class GetItemsByCategoryUseCase@Inject constructor(private val meliRepository: MeliRepository) {

    suspend operator fun invoke(itemID: String): Response<ItemListModel> {
        return meliRepository.getItemsByCategory(itemID)
    }
}