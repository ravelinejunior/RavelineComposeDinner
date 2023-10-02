package br.com.alura.raveline.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.raveline.sampledata.sampleProducts
import br.com.alura.raveline.ui.screens.HighlightsListScreen

fun NavGraphBuilder.highlightsListScreen(navController: NavHostController) {
    composable(AppDestination.TrendsHighlightRoute.route) {
        HighlightsListScreen(
            productModels = sampleProducts.sortedBy {
                it.name
            },
            onNavigateProductClick = { product ->
                val promoCode = "Fif"
                navController.navigateToProductDetails(product.id)
            },
            onNavigateOrderClick = {
                navController.navigate(AppDestination.CheckoutRoute.route)
            }
        )
    }
}

fun NavController.navigateToHighlightList() {
    navigate(AppDestination.TrendsHighlightRoute.route)
}