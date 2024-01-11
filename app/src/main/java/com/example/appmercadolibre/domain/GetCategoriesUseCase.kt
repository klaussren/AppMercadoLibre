package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.CategoriesModel
import javax.inject.Inject

/**
 * Caso de uso para obtener la lista de categorías.
 *
 * @param meliRepository Repositorio que proporciona la funcionalidad para obtener las categorías.
 */
class GetCategoriesUseCase @Inject constructor(private val meliRepository: MeliRepository) {

    /**
     * Invoca el caso de uso para obtener la lista de categorías.
     *
     * @return Lista de objetos [CategoriesModel] que representan las categorías disponibles.
     */
    suspend operator fun invoke(): List<CategoriesModel> {
        return meliRepository.getCategories()
    }
}