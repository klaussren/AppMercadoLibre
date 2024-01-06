package com.example.appmercadolibre.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmercadolibre.data.model.CategoriesModel
import com.example.appmercadolibre.data.model.ChildrenCategoriesModel
import com.example.appmercadolibre.domain.GetCategoriesUseCase
import com.example.appmercadolibre.domain.GetChildrenCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductShearchViewModel @Inject constructor (
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getChildrenCategoriesUseCase: GetChildrenCategoriesUseCase

) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoriesModel>>(emptyList())
    val categories: StateFlow<List<CategoriesModel>> get() = _categories

    private val _childrenCategories = MutableStateFlow<List<ChildrenCategoriesModel>>(emptyList())
    val childrenCategories: StateFlow<List<ChildrenCategoriesModel>> get() = _childrenCategories


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
            } catch (e: Exception) {
                _error.value = "Error fetching children categories: ${e.message}"
            }
        }
    }

}