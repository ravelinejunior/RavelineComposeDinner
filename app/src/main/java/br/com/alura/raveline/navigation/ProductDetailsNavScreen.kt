package br.com.alura.raveline.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import br.com.alura.raveline.navigation.nav_host.genUri
import br.com.alura.raveline.ui.screens.ProductDetailsScreen
import br.com.alura.raveline.ui.viewmodel.ProductDetailsViewModel
import java.math.BigDecimal

internal const val productIdArgument = "productId"
internal const val promoCodeArgument = "promoCode"
const val productDetailsRoute = "ProductDetails"

fun NavGraphBuilder.productDetailsScreen(
    onNavigateToCheckout: () -> Unit,
    onPopBackStack: () -> Unit
) {

    composable(
        "$productDetailsRoute/{$productIdArgument}?$promoCodeArgument={$promoCodeParam}",
        arguments = listOf(navArgument(promoCodeParam) {
            nullable = true
        }),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "$genUri/$productDetailsRoute/{$productIdArgument}?$promoCodeArgument={$promoCodeParam}"
            }
        )
    ) { backStackEntry ->

        val viewModel: ProductDetailsViewModel = viewModel(
            factory = ProductDetailsViewModel.ProductDetailsViewModelFactory
        )
        val uiState by viewModel.uiState.collectAsState()

        backStackEntry.arguments?.getString(productIdArgument)?.let { id ->

            /*LaunchedEffect(Unit) {
                viewModel.findProductById(id)
            }*/

            //Discount Product
            val promoCode = backStackEntry.arguments?.getString(promoCodeParam)

            val discount = when (promoCode) {
                "Coit" -> BigDecimal(0.1)
                "Banana" -> BigDecimal(0.2)
                "Fif" -> BigDecimal(0.5)
                else -> BigDecimal.ZERO
            }

            ProductDetailsScreen(

                uiState = uiState,
                discount = discount,
                onNavigateToCheckout = onNavigateToCheckout,
                onRetryProduct = {
                    viewModel.findProductById(id)
                },
                onBackStack = onPopBackStack
            )
        } ?: LaunchedEffect(Unit) {
            onPopBackStack()
        }

    }
}

fun NavController.navigateToProductDetails(productId: String, promoCode: String = String()) {
    navigate("$productDetailsRoute/$productId?$promoCodeArgument=$promoCode")
}