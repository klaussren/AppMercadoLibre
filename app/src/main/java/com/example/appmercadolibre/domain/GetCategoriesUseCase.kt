package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.CategoriesModel
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val meliRepository: MeliRepository) {

    suspend operator fun invoke(): List<CategoriesModel> {
        return meliRepository.getCategories()
    }
}