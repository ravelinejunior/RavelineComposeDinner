package br.com.alura.raveline.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.raveline.dao.ProductDao
import br.com.alura.raveline.ui.uistate.ProductDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductDetailsViewModel(
    private val dao: ProductDao = ProductDao()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun findProductById(id: String) {
        dao.findById(id).let { product ->
            _uiState.update {
                it.copy(product = product)
            }
        }
    }

}