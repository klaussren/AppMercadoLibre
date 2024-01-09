package com.example.appmercadolibre.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmercadolibre.data.model.ItemListModel
import com.example.appmercadolibre.data.model.ItemsModel
import com.example.appmercadolibre.domain.ConnectivityUseCase
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
    private val searchItemsUseCase: SearchItemsUseCase,
    private val connectivityUseCase: ConnectivityUseCase
) : ViewModel() {

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _startSearch = MutableStateFlow(false)
    val startSearch = _startSearch.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val  _showCategory = MutableStateFlow(true)
    val showCategory = _showCategory.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()


    private val _searchResults = MutableStateFlow<Response<ItemListModel>>(Response.success(null))
    val searchResults = _searchResults.asStateFlow()


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

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

    fun setSearchText(text: String){
        _searchText.value = text
    }

    fun setShowCategory(value: Boolean){
        _showCategory.value = value
    }



    fun checkConnectivity() {
        viewModelScope.launch {
            _isConnected.value = connectivityUseCase()
        }
    }
}