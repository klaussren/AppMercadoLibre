package com.example.appmercadolibre.domain

import com.example.appmercadolibre.data.MeliRepository
import com.example.appmercadolibre.data.model.ItemDetailModel
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

class GetItemDetailUseCaseTest{

    // Inicializar las dependencias simuladas
    @RelaxedMockK
    private lateinit var meliRepository: MeliRepository

    private lateinit var getItemDetailUseCase: GetItemDetailUseCase

    @RelaxedMockK
    private val mockResponse: Response<ItemDetailModel> = mockk()

    @Before
    fun setup() {
        // Inicializar las dependencias simuladas
        MockKAnnotations.init(this)
        getItemDetailUseCase = GetItemDetailUseCase(meliRepository)
    }
    @Test
    fun `invoke() should return Item Detail`()= runBlocking {
        // Given
        val fakeItemID = "1"
        val fakeItemDetail = mockResponse
        coEvery { meliRepository.getItemDetail(fakeItemID) } returns fakeItemDetail

        // When
        val result = getItemDetailUseCase(fakeItemID)

        // Then
        assertEquals(fakeItemDetail, result)
    }

    @Test
    fun `invoke() should return an empty response on exception`() = runBlocking {
        // Given
        // Configurar el comportamiento del repositorio para lanzar una excepción
        coEvery { meliRepository.getItemDetail(any()) } throws Exception("Test exception")

        // When
        val result = runCatching { getItemDetailUseCase("example query") }


        // Then
        assertTrue(result.isFailure)
    }



}