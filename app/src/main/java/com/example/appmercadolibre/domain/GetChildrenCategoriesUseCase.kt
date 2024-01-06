package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.CategoriesModel
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import javax.inject.Inject

class GetChildrenCategoriesUseCase @Inject constructor(private val meliRepository: MeliRepository) {

    suspend operator fun invoke(categoryID: String): ChildrenCategoriesModel {
        return meliRepository.getChildrenCategories(categoryID)
    }

}