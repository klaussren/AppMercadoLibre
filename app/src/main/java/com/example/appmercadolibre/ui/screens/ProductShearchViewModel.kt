package com.example.appmercadolibre.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmercadolibre.data.model.CategoriesModel
import com.example.appmercadolibre.domain.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductShearchViewModel @Inject constructor (private val getCategoriesUseCase: GetCategoriesUseCase) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoriesModel>>(emptyList())
    val categories: StateFlow<List<CategoriesModel>> get() = _categories


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



}