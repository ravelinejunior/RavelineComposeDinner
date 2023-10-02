package br.com.alura.raveline.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.raveline.sampledata.sampleWomen
import br.com.alura.raveline.ui.screens.CheckoutScreen

fun NavGraphBuilder.checkoutScreen(navController: NavHostController) {
    composable(AppDestination.CheckoutRoute.route) {
        CheckoutScreen(
            productModels = sampleWomen,
            onPopBackStack = {
                navController.navigateUp()
            },
        )
    }
}

fun NavController.navigateToCheckout(){
    navigate(AppDestination.CheckoutRoute.route)
}