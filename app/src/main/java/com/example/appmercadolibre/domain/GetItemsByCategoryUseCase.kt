package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.ItemListModel
import retrofit2.Response
import javax.inject.Inject

/**
 * Caso de uso para obtener una lista de Items por categoría.
 *
 * @param meliRepository Repositorio que proporciona la funcionalidad para obtener Items por categoría.
 */
class GetItemsByCategoryUseCase@Inject constructor(private val meliRepository: MeliRepository) {

    /**
     * Invoca el caso de uso para obtener una lista de Items por categoría.
     *
     * @param itemID Identificador de la categoría de la cual se desean obtener los iTEMS.
     * @return Objeto [Response] que contiene la lista de Items representada por [ItemListModel].
     */
    suspend operator fun invoke(itemID: String): Response<ItemListModel> {
        return meliRepository.getItemsByCategory(itemID)
    }
}