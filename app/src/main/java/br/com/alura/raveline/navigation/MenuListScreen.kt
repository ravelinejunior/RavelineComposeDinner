package br.com.alura.raveline.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.raveline.sampledata.sampleProducts
import br.com.alura.raveline.sampledata.sampleWomen
import br.com.alura.raveline.ui.screens.MenuListScreen


fun NavGraphBuilder.menuListScreen(navController: NavHostController) {
    composable(AppDestination.MenuRoute.route) {
        MenuListScreen(
            productModels = sampleProducts + sampleWomen.shuffled(),
            onNavigateToDetails = { product ->
                val promoCode = "Banana"
                navController.navigateToProductDetails(product.id, promoCode)
            }
        )
    }
}

fun NavController.navigateMenuList() {
    navigate(AppDestination.MenuRoute.route)
}