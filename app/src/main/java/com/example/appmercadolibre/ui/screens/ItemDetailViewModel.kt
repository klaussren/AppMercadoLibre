package com.example.appmercadolibre.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmercadolibre.data.model.ItemDetailModel
import com.example.appmercadolibre.domain.ConnectivityUseCase
import com.example.appmercadolibre.domain.GetItemDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/**
 * ViewModel que gestiona la lógica de la pantalla de detalles del Item.
 *
 * @param getItemDetailUseCase Caso de uso para obtener detalles del Item.
 * @param connectivityUseCase Caso de uso para verificar la conectividad a Internet.
 */
@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val getItemDetailUseCase: GetItemDetailUseCase,
    private val connectivityUseCase: ConnectivityUseCase
) : ViewModel() {

    // Estado del resultado de la búsqueda de detalles del Item
    private val _result = MutableStateFlow<Response<ItemDetailModel>>(Response.success(null))
    val result = _result.asStateFlow()
    // Estado de la conectividad a Internet
    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    // Estado de errores en la búsqueda de detalles del Item
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    /**
     * Realiza la búsqueda de detalles del Item según el ID proporcionado.
     *
     * @param id ID del Item.
     */
    fun getItemDetail(id: String) {
        viewModelScope.launch {
            try {
            // Invoca el caso de uso para obtener detalles del Item
                val resultItem = getItemDetailUseCase(id)
                _result.value = resultItem

            } catch (e: Exception) {
                // Manejo de errores y actualización del estado de error
                _error.value = "Error searching items: ${e.message}"
                Log.e("DetailItemViewModel", "${e.message}")
            }
        }
    }
    /**
     * Limpia el resultado actual de la búsqueda de detalles del Item.
     */
    fun clearResult() {
        _result.value = Response.success(null)
    }
    /**
     * Verifica la conectividad a Internet y actualiza el estado correspondiente.
     */
    fun checkConnectivity() {
        viewModelScope.launch {
            _isConnected.value = connectivityUseCase()
        }
    }


}