package br.com.alura.raveline.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navOptions
import androidx.navigation.navigation
import br.com.alura.raveline.navigation.BottomAppBarItem
import br.com.alura.raveline.navigation.drinksListScreen
import br.com.alura.raveline.navigation.drinksRoute
import br.com.alura.raveline.navigation.highLightsRoute
import br.com.alura.raveline.navigation.highlightsListScreen
import br.com.alura.raveline.navigation.menuListScreen
import br.com.alura.raveline.navigation.menuRoute
import br.com.alura.raveline.navigation.navigateMenuList
import br.com.alura.raveline.navigation.navigateToDrinksList
import br.com.alura.raveline.navigation.navigateToHighlightList

internal const val homeGraphRoute = "HomeGraph"

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(
        startDestination = highLightsRoute,
        route = homeGraphRoute
    ) {
        highlightsListScreen(navController)
        menuListScreen(navController)
        drinksListScreen(navController)
    }
}

fun NavController.navigateToHomeGraph(){
    navigate(homeGraphRoute)
}

fun NavController.navigateSingleTopWithPopUpTo(
    item: BottomAppBarItem,
) {
    val (route, navigate) = when (item) {

        BottomAppBarItem.HighlightListItemBar -> Pair(
            highLightsRoute,
            ::navigateToHighlightList
        )

        BottomAppBarItem.MenuItemBar -> Pair(
            menuRoute,
            ::navigateMenuList
        )

        BottomAppBarItem.DrinksItemBar -> Pair(
            drinksRoute,
            ::navigateToDrinksList
        )
    }

    val navOptions = navOptions {
        launchSingleTop = true
        popUpTo(route)
    }

    navigate(navOptions)
}