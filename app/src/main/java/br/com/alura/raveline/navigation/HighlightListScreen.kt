package br.com.alura.raveline.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.alura.raveline.sampledata.sampleProducts
import br.com.alura.raveline.ui.screens.HighlightsListScreen

const val highLightsRoute = "HighLights"

fun NavGraphBuilder.highlightsListScreen(navController: NavController) {
    composable(highLightsRoute) {
        HighlightsListScreen(
            productModels = sampleProducts.sortedBy {
                it.name
            },
            onNavigateProductClick = { product ->
                val promoCode = "Fif"
                navController.navigateToProductDetails(product.id)
            },
            onNavigateOrderClick = {
                navController.navigate(highLightsRoute)
            }
        )
    }
}

fun NavController.navigateToHighlightList(
    navOptions: NavOptions? = null
) {
    navigate(highLightsRoute, navOptions)
}