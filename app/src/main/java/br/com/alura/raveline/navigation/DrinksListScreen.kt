package br.com.alura.raveline.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.alura.raveline.sampledata.sampleDrinks
import br.com.alura.raveline.sampledata.sampleWomen
import br.com.alura.raveline.ui.screens.DrinksListScreen

const val drinksRoute = "Drinks"

fun NavGraphBuilder.drinksListScreen(navController: NavController) {
    composable(drinksRoute) {
        DrinksListScreen(
            productModels = sampleWomen + sampleDrinks,
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