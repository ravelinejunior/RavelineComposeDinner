package br.com.alura.raveline.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.com.alura.raveline.dao.ProductDao
import br.com.alura.raveline.navigation.productIdArgument
import br.com.alura.raveline.navigation.promoCodeParam
import br.com.alura.raveline.ui.uistate.ProductDetailsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import kotlin.random.Random

class ProductDetailsViewModel(
    private val dao: ProductDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailsUiState>(
        ProductDetailsUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private var discount = BigDecimal.ZERO

    init {
        viewModelScope.launch {

            val promoCode = savedStateHandle.get<String?>(promoCodeParam)

            discount = when (promoCode) {
                "Coit" -> BigDecimal(0.1)
                "Banana" -> BigDecimal(0.2)
                "Fif" -> BigDecimal(0.5)
                else -> BigDecimal.ZERO
            }

            savedStateHandle
                .getStateFlow<String?>(productIdArgument, null)
                .filterNotNull()
                .collect { id ->
                    findProductById(id)
                }
        }
    }

    fun findProductById(id: String) = viewModelScope.launch {

        val timeMillis = Random.nextLong(500, 2000)
        delay(timeMillis)

        _uiState.update {
            ProductDetailsUiState.Loading
        }

        val dataState = dao.findById(id)?.let { productModel ->
            val priceWithDiscount = productModel.price - (productModel.price * discount)
            ProductDetailsUiState.Success(productModel.copy(price = priceWithDiscount))
        } ?: ProductDetailsUiState.Failure("Product is not available.")

        _uiState.update {
            dataState
        }
    }

    companion object {
        val ProductDetailsViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ProductDetailsViewModel(
                    dao = ProductDao(),
                    savedStateHandle = createSavedStateHandle()
                )
            }
        }
    }

}