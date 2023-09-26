package br.com.alura.raveline.model

import br.com.alura.raveline.sampledata.perfectUrl
import java.math.BigDecimal

class ProductModel(
    val name: String,
    val price: BigDecimal,
    val description: String,
    val image: String? = perfectUrl
)