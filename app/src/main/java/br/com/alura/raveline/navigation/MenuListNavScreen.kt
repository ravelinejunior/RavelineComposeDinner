package br.com.alura.raveline.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.alura.raveline.model.ProductModel
import br.com.alura.raveline.ui.screens.MenuListScreen
import br.com.alura.raveline.ui.viewmodel.MenuListViewModel

const val menuRoute = "Menu"

fun NavGraphBuilder.menuListScreen(
    onNavigateToProductDetails: (ProductModel) -> Unit
) {
    composable(menuRoute) {
        val viewModel: MenuListViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()
        MenuListScreen(
            uiState = uiState,
            onProductClick = onNavigateToProductDetails,
        )
    }
}

fun NavController.navigateMenuList(
    navOptions: NavOptions? = null
) {
    navigate(menuRoute, navOptions)
}