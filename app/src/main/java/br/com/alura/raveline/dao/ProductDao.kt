package br.com.alura.raveline.dao

import br.com.alura.raveline.sampledata.sampleProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductDao {

    val products = MutableStateFlow(sampleProducts).asStateFlow()

    fun findById(productId: String) =
        products.value.firstOrNull {
            it.id == productId
        }

}