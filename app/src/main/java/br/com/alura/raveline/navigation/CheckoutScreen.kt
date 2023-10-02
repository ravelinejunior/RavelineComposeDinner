package br.com.alura.raveline.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.raveline.sampledata.sampleWomen
import br.com.alura.raveline.ui.screens.CheckoutScreen

const val checkoutRoute = "Checkout"

fun NavGraphBuilder.checkoutScreen(navController: NavHostController) {
    composable(checkoutRoute) {
        CheckoutScreen(
            productModels = sampleWomen,
            onPopBackStack = {
                navController.navigateUp()
            },
        )
    }
}

fun NavController.navigateToCheckout(){
    navigate(checkoutRoute)
}