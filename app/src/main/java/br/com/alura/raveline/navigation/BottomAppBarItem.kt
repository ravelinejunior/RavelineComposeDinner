package br.com.alura.raveline.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.LocalBar
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomAppBarItem(
    val label: String,
    val icon: ImageVector,
    val destination: String,
) {
    object HighlightListItemBar : BottomAppBarItem(
        label = "Trends",
        icon = Icons.Filled.AutoAwesome,
        destination = highLightsRoute
    )

    object MenuItemBar : BottomAppBarItem(
        label = "Menu",
        icon = Icons.Filled.RestaurantMenu,
        destination = menuRoute
    )

    object DrinksItemBar : BottomAppBarItem(
        label = "Drinks",
        icon = Icons.Outlined.LocalBar,
        destination = drinksRoute
    )
}

val bottomAppBarItems = listOf(
    BottomAppBarItem.HighlightListItemBar,
    BottomAppBarItem.MenuItemBar,
    BottomAppBarItem.DrinksItemBar
)
