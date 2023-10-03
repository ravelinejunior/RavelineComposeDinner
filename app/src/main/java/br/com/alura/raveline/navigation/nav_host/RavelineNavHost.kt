package br.com.alura.raveline.navigation.nav_host

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.alura.raveline.navigation.checkoutScreen
import br.com.alura.raveline.navigation.graph.homeGraph
import br.com.alura.raveline.navigation.graph.homeGraphRoute
import br.com.alura.raveline.navigation.productDetailsScreen

const val TAG: String = "RavelineNavHost"

@Composable
fun RavelineNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = homeGraphRoute,
    ) {
        homeGraph(navController)
        productDetailsScreen(navController)
        checkoutScreen(navController)
    }
}

