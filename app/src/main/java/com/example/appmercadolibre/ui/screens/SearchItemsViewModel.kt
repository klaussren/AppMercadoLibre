package com.example.appmercadolibre.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmercadolibre.data.model.ItemListModel
import com.example.appmercadolibre.domain.ConnectivityUseCase
import com.example.appmercadolibre.domain.SearchItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/**
 * ViewModel que gestiona la lógica y el estado relacionados con la búsqueda de artículos.
 *
 * @param searchItemsUseCase Caso de uso para buscar Items.
 * @param connectivityUseCase Caso de uso para verificar la conectividad a Internet.
 */
@HiltViewModel
class SearchItemsViewModel @Inject constructor(
    private val searchItemsUseCase: SearchItemsUseCase,
    private val connectivityUseCase: ConnectivityUseCase
) : ViewModel() {
    // Estado que indica si la búsqueda está activa.
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    // Estado que indica si se ha iniciado una búsqueda.
    private val _startSearch = MutableStateFlow(false)
    val startSearch = _startSearch.asStateFlow()

    // Estado que representa el texto de búsqueda actual.
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    // Estado que indica si se debe mostrar la búsqueda por categoría.
    private val  _showCategory = MutableStateFlow(true)
    val showCategory = _showCategory.asStateFlow()

    // Estado que indica si hay conectividad a Internet.
    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    // Estado que contiene los resultados de la búsqueda.
    private val _searchResults = MutableStateFlow<Response<ItemListModel>>(Response.success(null))
    val searchResults = _searchResults.asStateFlow()

    // Estado que contiene mensajes de error.
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    /**
     * Función para realizar una búsqueda de Items según la consulta proporcionada.
     *
     * @param query Consulta utilizada para buscar Items.
     */
    fun searchItems(query: String) {
        viewModelScope.launch {
            try {
                _startSearch.value= true
                val searchResults = searchItemsUseCase(query)
                _searchResults.value = searchResults
                _startSearch.value = false

            } catch (e: Exception) {
                _error.value = "Error searching items: ${e.message}"
            }
        }
    }

    /**
     * Función para realizar una búsqueda de Items icono del teclado.
     *
     * @param query Consulta utilizada para buscar Items.
     */
    fun searchItemsOnBoard(query: String) {
        viewModelScope.launch {
            try {
                _showCategory.value = false
                _isSearching.value = false
                _startSearch.value= true
                val searchResults = searchItemsUseCase(query)
                _searchResults.value = searchResults
                _startSearch.value = false

            } catch (e: Exception) {
                _error.value = "Error searching items: ${e.message}"
                Log.e("SearchItemsViewModel", "${e.message}")
            }
        }
    }

    /**
     * Función para actualizar el texto de búsqueda y realizar una búsqueda.
     *
     * @param text Nuevo texto de búsqueda.
     */
    fun onSearchTextChange(text: String) {
        _searchText.value = text
        searchItems(text)
    }

    /**
     * Función para cambiar el estado de búsqueda (activar/desactivar).
     */
    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    /**
     * Función para establecer el estado de búsqueda.
     *
     * @param value Nuevo estado de búsqueda.
     */
    fun isSearching(value: Boolean){
        _isSearching.value = value
    }

    /**
     * Función para establecer el texto de búsqueda.
     *
     * @param text Nuevo texto de búsqueda.
     */
    fun setSearchText(text: String){
        _searchText.value = text
    }

    /**
     * Función para establecer el estado de mostrar la búsqueda por categoría.
     *
     * @param value Nuevo estado de mostrar la búsqueda por categoría.
     */
    fun setShowCategory(value: Boolean){
        _showCategory.value = value
    }


    /**
     * Función para verificar la conectividad a Internet.
     */
    fun checkConnectivity() {
        viewModelScope.launch {
            _isConnected.value = connectivityUseCase()
        }
    }
}