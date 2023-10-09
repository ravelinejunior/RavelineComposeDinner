package br.com.alura.raveline.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.alura.raveline.model.ProductModel
import br.com.alura.raveline.ui.screens.HighlightsListScreen
import br.com.alura.raveline.ui.viewmodel.HighlightsListViewModel

const val highLightsRoute = "HighLights"

fun NavGraphBuilder.highlightsListScreen(
    onNavigateToCheckout: () -> Unit,
    onNavigateToProductDetails: (ProductModel) -> Unit
) {
    composable(highLightsRoute) {
        val viewModel: HighlightsListViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()
        HighlightsListScreen(
            uiState = uiState,
            onNavigateProductClick = onNavigateToProductDetails,
            onOrderClick = onNavigateToCheckout,
        )
    }
}

fun NavController.navigateToHighlightList(
    navOptions: NavOptions? = null
) {
    navigate(highLightsRoute, navOptions)
}