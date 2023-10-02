package br.com.alura.raveline.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.raveline.sampledata.sampleDrinks
import br.com.alura.raveline.sampledata.sampleWomen
import br.com.alura.raveline.ui.screens.DrinksListScreen

fun NavGraphBuilder.drinksListScreen(navController: NavHostController) {
    composable(AppDestination.DrinksRoute.route) {
        DrinksListScreen(
            productModels = sampleWomen + sampleDrinks,
            onNavigateToDetails = { product ->
                val promoCode = "Coit"
                navController.navigateToProductDetails(product.id, promoCode)
            }
        )
    }

    fun NavController.navigateToDrinksList() {
        navigate(AppDestination.DrinksRoute.route)
    }
}