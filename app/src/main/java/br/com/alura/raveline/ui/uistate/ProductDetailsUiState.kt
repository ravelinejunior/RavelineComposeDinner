package br.com.alura.raveline.ui.uistate

import br.com.alura.raveline.model.ProductModel

sealed class ProductDetailsUiState {

    object Loading : ProductDetailsUiState()

    class Failure(val message: String = String()) : ProductDetailsUiState()

    class Success(val productModel: ProductModel) : ProductDetailsUiState()

}