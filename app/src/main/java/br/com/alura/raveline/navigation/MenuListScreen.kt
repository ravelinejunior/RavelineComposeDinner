package br.com.alura.raveline.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.alura.raveline.sampledata.sampleProducts
import br.com.alura.raveline.sampledata.sampleWomen
import br.com.alura.raveline.ui.screens.MenuListScreen

const val menuRoute = "Menu"

fun NavGraphBuilder.menuListScreen(navController: NavController) {
    composable(menuRoute) {
        MenuListScreen(
            productModels = sampleProducts + sampleWomen.shuffled(),
            onNavigateToDetails = { product ->
                val promoCode = "Banana"
                navController.navigateToProductDetails(product.id, promoCode)
            }
        )
    }
}

fun NavController.navigateMenuList(
    navOptions: NavOptions? = null
) {
    navigate(menuRoute, navOptions)
}