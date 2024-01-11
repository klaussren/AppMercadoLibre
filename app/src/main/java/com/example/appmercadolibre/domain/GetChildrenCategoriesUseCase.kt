package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import javax.inject.Inject

/**
 * Caso de uso para obtener la categoria pero con un valor mas para la imagen.
 *
 * @param meliRepository Repositorio que proporciona la funcionalidad para obtener pero con un valor mas para la imagen.
 */
class GetChildrenCategoriesUseCase @Inject constructor(private val meliRepository: MeliRepository) {

    /**
     * Invoca el caso de uso para obtener la categoria pero con un valor mas para la imagen.
     *
     * @param categoryID Identificador de la categoría de la cual se desean obtener la categoria pero con un valor mas para la imagen.
     * @return Objeto [ChildrenCategoriesModel] que representa la categoria pero con un valor mas para la imagen.
     */
    suspend operator fun invoke(categoryID: String): ChildrenCategoriesModel {
        return meliRepository.getChildrenCategories(categoryID)
    }

}