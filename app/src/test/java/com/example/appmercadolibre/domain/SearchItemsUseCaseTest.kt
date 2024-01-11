package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.ItemListModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class SearchItemsUseCaseTest {
    @RelaxedMockK
    private lateinit var meliRepository: MeliRepository

    private lateinit var searchItemsUseCase: SearchItemsUseCase
    @RelaxedMockK
    private val mockResponse: Response<ItemListModel> = mockk()


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        searchItemsUseCase = SearchItemsUseCase(meliRepository)
    }


    @Test
    fun `invoke() should return the repository response`() = runBlocking {
        // Given
        coEvery { meliRepository.searchItemsByQuery(any()) } returns mockResponse

        // When
        val result = searchItemsUseCase("example query")

        // Then
        assertEquals(mockResponse, result)
    }

    @Test
    fun `invoke() should return an empty response on exception`() = runBlocking {
        // Given
        // Configurar el comportamiento del repositorio para lanzar una excepción
        coEvery { meliRepository.searchItemsByQuery(any()) } throws Exception("Test exception")

        // When
        val result = runCatching { searchItemsUseCase("example query") }


        // Then
        assertTrue(result.isFailure)
    }

}