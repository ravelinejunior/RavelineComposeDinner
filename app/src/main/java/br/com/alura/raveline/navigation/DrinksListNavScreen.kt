package br.com.alura.raveline.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import br.com.alura.raveline.model.ProductModel
import br.com.alura.raveline.navigation.nav_host.genUri
import br.com.alura.raveline.ui.screens.DrinksListScreen
import br.com.alura.raveline.ui.viewmodel.DrinksListViewModel

const val drinksRoute = "Drinks"


fun NavGraphBuilder.drinksListScreen(
    onNavigateToProductDetails: (ProductModel) -> Unit
) {
    composable(
        drinksRoute,
        deepLinks = listOf(navDeepLink {
            uriPattern = genUri
        })
    ) {
        val viewModel: DrinksListViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()
        DrinksListScreen(
            uiState = uiState,
            onProductClick = onNavigateToProductDetails,
        )
    }
}


fun NavController.navigateToDrinksList(
    navOptions: NavOptions? = null
) {
    navigate(drinksRoute, navOptions)
}