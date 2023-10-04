package br.com.alura.raveline.ui.uistate

import br.com.alura.raveline.model.ProductModel

data class HighlightsListUiState(
    val products: List<ProductModel> = emptyList()
)