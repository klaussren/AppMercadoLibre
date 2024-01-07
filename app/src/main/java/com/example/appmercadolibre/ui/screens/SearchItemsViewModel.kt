package com.example.appmercadolibre.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmercadolibre.data.model.ItemListModel
import com.example.appmercadolibre.data.model.ItemsModel
import com.example.appmercadolibre.domain.SearchItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchItemsViewModel @Inject constructor(
    private val searchItemsUseCase: SearchItemsUseCase
) : ViewModel() {

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()


    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()


    private val _searchResults = MutableStateFlow<Response<ItemListModel>>(Response.success(null))
    val searchResults = _searchResults.asStateFlow()

    //val searchResults: StateFlow<List<ItemsModel>> get() = _searchResults

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun searchItems(query: String) {
        viewModelScope.launch {
            try {

                val searchResults = searchItemsUseCase(query)
                _searchResults.value = searchResults

            } catch (e: Exception) {
                _error.value = "Error searching items: ${e.message}"
            }
        }
    }
    fun searchItemsOnBoard(query: String) {
        viewModelScope.launch {
            try {
                _isSearching.value = false
                val searchResults = searchItemsUseCase(query)
                _searchResults.value = searchResults

            } catch (e: Exception) {
                _error.value = "Error searching items: ${e.message}"
            }
        }
    }


    fun onSearchTextChange(text: String) {
        _searchText.value = text
        searchItems(text)
    }

    fun onToogleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    fun isSearching(value: Boolean){
        _isSearching.value = value
    }

}