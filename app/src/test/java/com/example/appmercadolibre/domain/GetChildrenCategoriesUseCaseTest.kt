package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetChildrenCategoriesUseCaseTest {

    // Inicializar las dependencias simuladas
    @RelaxedMockK
    private lateinit var meliRepository: MeliRepository

    private lateinit var getChildrenCategoriesUseCase: GetChildrenCategoriesUseCase

    @Before
    fun setup() {
        // Inicializar las dependencias simuladas
        MockKAnnotations.init(this)
        getChildrenCategoriesUseCase = GetChildrenCategoriesUseCase(meliRepository)
    }

    @Test
    fun `invoke() should return children categories`() = runBlocking {
        // Given
        val fakeCategoryID = "1"
        val fakeChildrenCategories = ChildrenCategoriesModel("1", "Electrodomesticos","https://http2.mlstatic.com")
        coEvery { meliRepository.getChildrenCategories(fakeCategoryID) } returns fakeChildrenCategories

        // When
        val result = getChildrenCategoriesUseCase(fakeCategoryID)

        // Then
        assertEquals(fakeChildrenCategories, result)
    }

    @Test
    fun `invoke() should return an empty list when repository returns no data`() = runBlocking {
        // Given
        val fakeCategoryID = "1"
        val fakeChildrenCategories =ChildrenCategoriesModel(id = "", name = "", picture = "")
        coEvery { meliRepository.getChildrenCategories(fakeCategoryID) } returns fakeChildrenCategories

        // When
        val result = getChildrenCategoriesUseCase(fakeCategoryID)

        // Then
        assertEquals(fakeChildrenCategories, result)
    }

}