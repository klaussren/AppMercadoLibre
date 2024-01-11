package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.ItemListModel
import retrofit2.Response
import javax.inject.Inject

/**
 * Caso de uso para realizar una búsqueda de Items por consulta.
 *
 * @param meliRepository Repositorio que proporciona la funcionalidad para buscar Items por consulta.
 */
class SearchItemsUseCase @Inject constructor(private val meliRepository: MeliRepository) {

    /**
     * Invoca el caso de uso para realizar una búsqueda de Items por consulta.
     *
     * @param query Consulta utilizada para buscar Items.
     * @return Objeto [Response] que contiene la lista de Items resultante de la búsqueda, representada por [ItemListModel].
     */
    suspend operator fun invoke(query: String): Response<ItemListModel> {
        return meliRepository.searchItemsByQuery(query)
    }
}