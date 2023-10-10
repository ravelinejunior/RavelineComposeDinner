package br.com.alura.raveline.navigation.nav_host

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.alura.raveline.navigation.checkoutScreen
import br.com.alura.raveline.navigation.graph.homeGraph
import br.com.alura.raveline.navigation.graph.homeGraphRoute
import br.com.alura.raveline.navigation.navigateToCheckout
import br.com.alura.raveline.navigation.navigateToProductDetails
import br.com.alura.raveline.navigation.productDetailsScreen
import br.com.alura.raveline.utils.orderDoneKey

const val TAG: String = "RavelineNavHost"

@Composable
fun RavelineNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = homeGraphRoute,
    ) {
        homeGraph(
            onNavigateToCheckout = {
                navController.navigateToCheckout()
            },
            onNavigateToProductDetails = { product ->
                val promoCode = "Coit"
                navController.navigateToProductDetails(product.id, promoCode)
            }
        )
        productDetailsScreen(
            onNavigateToCheckout = {
                navController.navigateToCheckout()
            },
            onPopBackStack = {
                navController.navigateUp()
            }
        )
        checkoutScreen(
            onPopBackStack = {
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set(orderDoneKey, " ✅ Order successfully requested")
                navController.navigateUp()
            }
        )
    }
}

