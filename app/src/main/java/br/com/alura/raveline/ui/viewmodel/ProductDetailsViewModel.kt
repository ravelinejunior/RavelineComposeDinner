package br.com.alura.raveline.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.raveline.dao.ProductDao
import br.com.alura.raveline.ui.uistate.ProductDetailsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class ProductDetailsViewModel(
    private val dao: ProductDao = ProductDao()
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailsUiState>(
        ProductDetailsUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    fun findProductById(id: String) = viewModelScope.launch {

        val timeMillis = Random.nextLong(500,2000)
        delay(timeMillis)

        _uiState.update {
            ProductDetailsUiState.Loading
        }

        val dataState = dao.findById(id)?.let { productModel ->
            ProductDetailsUiState.Success(productModel)
        } ?: ProductDetailsUiState.Failure("Product is not available.")

        _uiState.update {
            dataState
        }
    }

}