package br.com.alura.raveline.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.alura.raveline.sampledata.sampleDrinks
import br.com.alura.raveline.sampledata.sampleWomen
import br.com.alura.raveline.ui.screens.DrinksListScreen
import br.com.alura.raveline.ui.viewmodel.DrinksListViewModel

const val drinksRoute = "Drinks"

fun NavGraphBuilder.drinksListScreen(navController: NavController) {
    composable(drinksRoute) {
        val viewModel: DrinksListViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()
        DrinksListScreen(
            uiState = uiState,
            onNavigateToDetails = { product ->
                val promoCode = "Coit"
                navController.navigateToProductDetails(product.id, promoCode)
            }
        )
    }
}


fun NavController.navigateToDrinksList(
    navOptions: NavOptions? = null
) {
    navigate(drinksRoute, navOptions)
}