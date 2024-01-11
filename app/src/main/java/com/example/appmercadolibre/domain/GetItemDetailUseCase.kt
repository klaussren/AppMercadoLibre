package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.ItemDetailModel
import retrofit2.Response
import javax.inject.Inject

/**
 * Caso de uso para obtener los detalles de un Items específico.
 *
 * @param meliRepository Repositorio que proporciona la funcionalidad para obtener detalles de Items.
 */
class GetItemDetailUseCase @Inject constructor(private val meliRepository: MeliRepository) {

    /**
     * Invoca el caso de uso para obtener los detalles de un artículo específico.
     *
     * @param itemID Identificador del Item del cual se desean obtener los detalles.
     * @return Objeto [Response] que contiene los detalles del Item representados por [ItemDetailModel].
     */
    suspend operator fun invoke(itemID: String): Response<ItemDetailModel> {
        return meliRepository.getItemDetail(itemID)
    }
}