package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.ItemDetailModel
import retrofit2.Response
import javax.inject.Inject

class GetItemDetailUseCase @Inject constructor(private val meliRepository: MeliRepository) {

    suspend operator fun invoke(itemID: String): Response<ItemDetailModel> {
        return meliRepository.getItemDetail(itemID)
    }
}