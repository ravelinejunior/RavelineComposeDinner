package br.com.alura.raveline.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.raveline.dao.ProductDao
import br.com.alura.raveline.ui.uistate.HighlightsListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class HighlightsListViewModel(
    private val dao: ProductDao = ProductDao()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HighlightsListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dao.products.map { pMap ->
                pMap.map { product ->
                    //To change the failure remove it
                    product.copy(
                        id = UUID.randomUUID().toString()
                    )
                }
            }.collect { products ->
                _uiState.update {
                    it.copy(products = products)
                }
            }

        }
    }

}