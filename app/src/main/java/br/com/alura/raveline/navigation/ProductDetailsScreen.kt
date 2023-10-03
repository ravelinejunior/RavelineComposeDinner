package br.com.alura.raveline.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.alura.raveline.navigation.nav_host.TAG
import br.com.alura.raveline.sampledata.sampleProducts
import br.com.alura.raveline.ui.screens.ProductDetailsScreen
import java.math.BigDecimal

private const val productIdArgument = "productId"
internal const val promoCodeArgument = "promoCode"
const val productDetailsRoute = "ProductDetails"

fun NavGraphBuilder.productDetailsScreen(navController: NavHostController) {
    composable(
        "$productDetailsRoute/{$productIdArgument}?$promoCodeArgument={$promoCodeParam}",
        arguments = listOf(navArgument(promoCodeParam) {
            nullable = true
        })
    ) { backStackEntry ->

        val id = backStackEntry.arguments?.getString(productIdArgument)

        //Get selected product
        val selectedProduct = sampleProducts.firstOrNull { productModel ->
            productModel.id == id
        }

        Log.i(TAG, "Product selected: $selectedProduct")

        //Discount Product
        val promoCode = backStackEntry.arguments?.getString(promoCodeParam)
        sampleProducts.find {
            it.id == id
        }?.let { productModel ->

            val discount = when (promoCode) {
                "Coit" -> BigDecimal(0.1)
                "Banana" -> BigDecimal(0.2)
                "Fif" -> BigDecimal(0.5)
                else -> BigDecimal.ZERO
            }

            val currentPrice = productModel.price

            ProductDetailsScreen(
                productModel = productModel.copy(price = currentPrice - (currentPrice * discount)),
                onNavigateToCheckout = {
                    navController.navigateToCheckout()
                }
            )
        } ?: LaunchedEffect(Unit) {
            navController.navigateUp()
        }

    }
}

fun NavController.navigateToProductDetails(productId: String, promoCode: String = String()) {
    navigate("$productDetailsRoute/$productId?$promoCodeArgument=$promoCode")
}