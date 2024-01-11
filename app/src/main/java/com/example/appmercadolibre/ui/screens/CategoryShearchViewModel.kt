package com.example.appmercadolibre.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmercadolibre.data.model.CategoriesModel
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import com.example.appmercadolibre.data.model.ItemListModel
import com.example.appmercadolibre.domain.ConnectivityUseCase
import com.example.appmercadolibre.domain.GetCategoriesUseCase
import com.example.appmercadolibre.domain.GetChildrenCategoriesUseCase
import com.example.appmercadolibre.domain.GetItemsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/**
 * ViewModel que gestiona la lógica relacionada con la búsqueda por categoría.
 *
 * @param getCategoriesUseCase Caso de uso para obtener las categorías principales.
 * @param getChildrenCategoriesUseCase Caso de uso para obtener las categorías con la imagen de una categoría.
 * @param getItemsByCategoryUseCase Caso de uso para obtener los Items de una categoría.
 * @param connectivityUseCase Caso de uso para verificar la conectividad a Internet.
 */
@HiltViewModel
class CategoryShearchViewModel @Inject constructor (
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getChildrenCategoriesUseCase: GetChildrenCategoriesUseCase,
    private val getItemsByCategoryUseCase: GetItemsByCategoryUseCase,
    private val connectivityUseCase: ConnectivityUseCase

) : ViewModel() {

    // Lista de categorías principales.
    private val _categories = MutableStateFlow<List<CategoriesModel>>(emptyList())

    // Resultado de categorías con imagen de la categoría actualmente seleccionada.
    private val _childrenCategories = MutableStateFlow<List<ChildrenCategoriesModel>>(emptyList())
    val childrenCategories: StateFlow<List<ChildrenCategoriesModel>> get() = _childrenCategories

    // Estado que indica si la búsqueda de categorías está en curso.
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    // Estado que indica si la búsqueda de Items está en curso.
    private val _isSearchingItems = MutableStateFlow(false)
    val isSearchingItems = _isSearchingItems.asStateFlow()

    // Resultados de la búsqueda de Items por categoría.
    private val _itemsResults = MutableStateFlow<Response<ItemListModel>>(Response.success(null))
    val itemsResults = _itemsResults.asStateFlow()

    // Estado que indica si hay conexión a Internet.
    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    // Estado que almacena mensajes de error.
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    /**
     * Método para obtener las categorías con la imagen segun la lista total de categorias.
     */
    fun fetchChildrenCategories() {
        viewModelScope.launch {
            try {
                _isSearching.value = true
                // Obtiene las categorías principales de forma asíncrona
                _categories.value = getCategoriesUseCase()

                // Utiliza async para realizar llamadas a getChildrenCategoriesUseCase de manera concurrente
                val deferredChildrenCategories = _categories.value.map { category ->
                    async {
                        getChildrenCategoriesUseCase(category.id)
                    }
                }

                // Espera a que todas las llamadas asíncronas se completen
                val allChildrenCategories = deferredChildrenCategories.awaitAll()

                // Actualiza el estado con todas las categorías hijas obtenidas
                _childrenCategories.value = allChildrenCategories
                _isSearching.value = false
            } catch (e: Exception) {
                _error.value = "Error fetching children categories: ${e.message}"
            }
        }
    }


    /**
     * Método para obtener los Items de una categoría.
     *
     * @param query Término de búsqueda.
     */
    fun getItemsByCategory(query: String) {
        viewModelScope.launch {
            try {
                _isSearchingItems.value = true
                val itemsResults = getItemsByCategoryUseCase(query)
                _itemsResults.value = itemsResults

            } catch (e: Exception) {
                _error.value = "Error searching items: ${e.message}"
            }
        }
    }


    /**
     * Método para verificar la conectividad a Internet.
     */
    fun checkConnectivity() {
        viewModelScope.launch {
            _isConnected.value = connectivityUseCase()
        }
    }

    /**
     * Método para cambiar el estado de búsqueda de Items.
     *
     * @param value Nuevo valor para el estado de búsqueda de Items.
     */
    fun setIsSearchingItems(value: Boolean){
        _isSearchingItems.value = value
    }

    /**
     * Método para limpiar la lista de categorías y resultados de búsqueda.
     */
    fun clearChildrenCategories(){
        _categories.value = emptyList()
        _childrenCategories.value = emptyList()
        _itemsResults.value = Response.success(null)
    }

}