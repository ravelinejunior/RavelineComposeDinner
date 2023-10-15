package br.com.alura.raveline.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.alura.raveline.ui.screens.CheckoutScreen
import br.com.alura.raveline.ui.viewmodel.CheckoutViewModel

internal val checkoutRoute = "Checkout"

fun NavGraphBuilder.checkoutScreen(
    onPopBackStack: () -> Unit
) {
    composable(checkoutRoute) {
        val viewModel: CheckoutViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()
        CheckoutScreen(
            uiState = uiState,
            onOrderClick = onPopBackStack,
        )
    }
}

fun NavController.navigateToCheckout() {
    navigate(checkoutRoute)
}