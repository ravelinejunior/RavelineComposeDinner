package br.com.alura.raveline.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

const val TAG: String = "RavelineNavHost"

@Composable
fun RavelineNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = highLightsRoute,
    ) {
        highlightsListScreen(navController)
        menuListScreen(navController)
        drinksListScreen(navController)
        productDetailsScreen(navController)
        checkoutScreen(navController)
    }
}

