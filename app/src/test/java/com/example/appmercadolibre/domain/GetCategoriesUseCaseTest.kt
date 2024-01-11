package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.CategoriesModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCategoriesUseCaseTest {

    @RelaxedMockK
    private lateinit var meliRepository: MeliRepository

    private lateinit var getCategoriesUseCase: GetCategoriesUseCase


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getCategoriesUseCase = GetCategoriesUseCase(meliRepository)
    }

    @Test
    fun `invoke() should return a list of categories when repository returns data`() = runBlocking {
        // Given
        val expectedCategories = listOf(
            CategoriesModel(id = "1", name = "Electrodomesticos"),
            CategoriesModel(id = "2", name = "Ropa"),
            CategoriesModel(id = "3", name = "Automobiles"),
            CategoriesModel(id = "4", name = "Libros y revistas")

        )
        coEvery { meliRepository.getCategories() } returns expectedCategories

        // When
        val result = getCategoriesUseCase()

        // Then
        assertEquals(expectedCategories, result)
    }

    @Test
    fun `invoke() should return an empty list when repository returns no data`() = runBlocking {
        // Given
        val expectedCategories = emptyList<CategoriesModel>()
        coEvery { meliRepository.getCategories() } returns expectedCategories

        // When
        val result: List<CategoriesModel> = getCategoriesUseCase()

        // Then
        assertEquals(expectedCategories, result)
    }
}