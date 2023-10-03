package br.com.alura.raveline.navigation

// Constants
const val promoCodeParam = "promoCode"

sealed class AppDestination(
    val route: String
) {
    object TrendsHighlightRoute : AppDestination(highLightsRoute)
    object MenuRoute : AppDestination(menuRoute)
    object DrinksRoute : AppDestination(drinksRoute)
    object ProductDetailsRoute : AppDestination(productDetailsRoute)
    object CheckoutRoute : AppDestination(checkoutRoute)
}




