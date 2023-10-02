package br.com.alura.raveline.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

val TAG: String = "RavelineNavHost"

@Composable
fun RavelineNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.TrendsHighlightRoute.route,
    ) {
        highlightsListScreen(navController)
        menuListScreen(navController)
        drinksListScreen(navController)
        productDetailsScreen(navController)
        checkoutScreen(navController)
    }

}
