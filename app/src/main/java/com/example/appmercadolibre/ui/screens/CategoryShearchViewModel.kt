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

@HiltViewModel
class CategoryShearchViewModel @Inject constructor (
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getChildrenCategoriesUseCase: GetChildrenCategoriesUseCase,
    private val getItemsByCategoryUseCase: GetItemsByCategoryUseCase,
    private val connectivityUseCase: ConnectivityUseCase

) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoriesModel>>(emptyList())


    private val _childrenCategories = MutableStateFlow<List<ChildrenCategoriesModel>>(emptyList())
    val childrenCategories: StateFlow<List<ChildrenCategoriesModel>> get() = _childrenCategories

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _isSearchingItems = MutableStateFlow(false)
    val isSearchingItems = _isSearchingItems.asStateFlow()


    private val _itemsResults = MutableStateFlow<Response<ItemListModel>>(Response.success(null))
    val itemsResults = _itemsResults.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                _categories.value = getCategoriesUseCase()
            } catch (e: Exception) {
                _error.value = "Error fetching categories: ${e.message}"
            }
        }
    }


    fun fetchChildrenCategories() {
        viewModelScope.launch {
            try {
                _isSearching.value = true
                // Obtén las categorías principales de forma asíncrona
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

    fun checkConnectivity() {
        viewModelScope.launch {
            _isConnected.value = connectivityUseCase()
        }
    }


    fun setIsSearchingItems(value: Boolean){
        _isSearchingItems.value = value
    }

    fun clearChildrenCategories(){
        _categories.value = emptyList()
        _childrenCategories.value = emptyList()
        _itemsResults.value = Response.success(null)
    }

}