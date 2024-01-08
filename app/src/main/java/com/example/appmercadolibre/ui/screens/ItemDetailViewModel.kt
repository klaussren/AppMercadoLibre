package com.example.appmercadolibre.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmercadolibre.data.model.ItemDetailModel
import com.example.appmercadolibre.data.model.ItemListModel
import com.example.appmercadolibre.domain.GetItemDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val getItemDetailUseCase: GetItemDetailUseCase
) : ViewModel() {

    private val _result = MutableStateFlow<Response<ItemDetailModel>>(Response.success(null))
    val result = _result.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun getItemDetail(id: String) {
        viewModelScope.launch {
            try {

                val resultItem = getItemDetailUseCase(id)
                _result.value = resultItem

            } catch (e: Exception) {
                _error.value = "Error searching items: ${e.message}"
            }
        }
    }


}