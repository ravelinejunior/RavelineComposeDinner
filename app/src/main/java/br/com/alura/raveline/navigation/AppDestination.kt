package br.com.alura.raveline.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.LocalBar
import br.com.alura.raveline.navigation.AppDestination.DrinksRoute
import br.com.alura.raveline.navigation.AppDestination.MenuRoute
import br.com.alura.raveline.navigation.AppDestination.TrendsHighlightRoute
import br.com.alura.raveline.ui.components.BottomAppBarItem

// Constants
const val promoCodeParam = "promoCode"

const val homeRoute = "Home"
const val drinksRoute = "Drinks"
const val highLightsRoute = "HighLights"
const val menuRoute = "Menu"
const val productDetailsRoute = "ProductDetails"
const val checkoutRoute = "Checkout"

sealed class AppDestination(
    val route: String
) {
    object TrendsHighlightRoute : AppDestination(highLightsRoute)
    object MenuRoute : AppDestination(menuRoute)
    object DrinksRoute : AppDestination(drinksRoute)
    object ProductDetailsRoute : AppDestination(productDetailsRoute)
    object CheckoutRoute : AppDestination(checkoutRoute)
}


val bottomAppBarItems = listOf(
    BottomAppBarItem(
        label = "Trends",
        icon = Icons.Filled.AutoAwesome,
        destination = TrendsHighlightRoute
    ),
    BottomAppBarItem(
        label = "Menu",
        icon = Icons.Filled.RestaurantMenu,
        destination = MenuRoute
    ),
    BottomAppBarItem(
        label = "Drinks",
        icon = Icons.Outlined.LocalBar,
        destination = DrinksRoute
    ),
)
