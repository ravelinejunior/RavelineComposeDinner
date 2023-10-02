package br.com.alura.raveline

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import br.com.alura.raveline.navigation.BottomAppBarItem
import br.com.alura.raveline.navigation.RavelineNavHost
import br.com.alura.raveline.navigation.bottomAppBarItems
import br.com.alura.raveline.navigation.checkoutRoute
import br.com.alura.raveline.navigation.drinksRoute
import br.com.alura.raveline.navigation.highLightsRoute
import br.com.alura.raveline.navigation.menuRoute
import br.com.alura.raveline.navigation.navigateMenuList
import br.com.alura.raveline.navigation.navigateToDrinksList
import br.com.alura.raveline.navigation.navigateToHighlightList
import br.com.alura.raveline.navigation.productDetailsRoute
import br.com.alura.raveline.sampledata.sampleProducts
import br.com.alura.raveline.ui.components.RavelineBottomAppBar
import br.com.alura.raveline.ui.screens.*
import br.com.alura.raveline.ui.theme.RavelineTheme

class MainActivity : ComponentActivity() {

    private val TAG: String? = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            // See the change in navigation
            LaunchedEffect(Unit) {
                navController.addOnDestinationChangedListener { _, _, _ ->
                    val routes = navController.backQueue.map {
                        it.destination.route
                    }
                    Log.i("MainActivityTAG", "onCreate: back stack - $routes")
                }
            }
            RavelineTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val backStackEntryState by navController.currentBackStackEntryAsState()
                    val currentDestination = backStackEntryState?.destination
                    val currentRoute = currentDestination?.route

                    val selectedItem by remember(currentDestination) {

                        val item = when (currentRoute) {
                            highLightsRoute -> BottomAppBarItem.HighlightListItemBar
                            menuRoute -> BottomAppBarItem.MenuItemBar
                            drinksRoute -> BottomAppBarItem.DrinksItemBar
                            else -> BottomAppBarItem.HighlightListItemBar
                        }

                        mutableStateOf(item)
                    }

                    //Verify if bottom app bar has items
                    val containsInBottomAppBarItems = when (currentRoute) {
                        highLightsRoute, menuRoute, drinksRoute -> true
                        else -> false
                    }

                    val isShowFab = when (currentDestination?.route) {
                        menuRoute,
                        drinksRoute -> true

                        else -> false
                    }

                    val isProductSelected = currentDestination?.let {
                        it.route?.contains(
                            productDetailsRoute,
                            ignoreCase = true
                        )
                    } ?: false

                    RavelineApp(
                        bottomAppBarItemSelected = selectedItem,
                        onBottomAppBarItemSelectedChange = { item ->

                            val (route, navigate) = when (item) {

                                BottomAppBarItem.HighlightListItemBar -> Pair(
                                    highLightsRoute,
                                    navController::navigateToHighlightList
                                )

                                BottomAppBarItem.MenuItemBar -> Pair(
                                    menuRoute,
                                    navController::navigateMenuList
                                )

                                BottomAppBarItem.DrinksItemBar -> Pair(
                                    drinksRoute,
                                    navController::navigateToDrinksList
                                )
                            }

                            val navOptions = navOptions {
                                launchSingleTop = true
                                popUpTo(route)
                            }

                            navigate(navOptions)
                        },
                        onFabClick = {
                            //Navigate to checkout
                            navController.navigate(checkoutRoute)
                        },
                        isShowBottomBar = containsInBottomAppBarItems,
                        isShowTopBar = containsInBottomAppBarItems,
                        isShowFab = isShowFab,
                        isProductDetailSelected = isProductSelected,
                        onNavigateBack = {
                            navController.navigateUp()
                        }
                    ) {
                        RavelineNavHost(navController = navController)
                    }
                }
            }
        }
    }

    @Composable
    private fun GenericNavigationInit() {
        val initialScreen = getString(R.string.trends)
        val screens = remember {
            mutableStateListOf(initialScreen)
        }
        Log.i(TAG, "onCreate: screens ${screens.toList()}")
        val currentScreen = screens.last()
        BackHandler(screens.size > 1) {
            screens.removeLast()
        }

        when (currentScreen) {
            getString(R.string.trends) -> HighlightsListScreen(
                productModels = sampleProducts,
                onNavigateOrderClick = {
                    screens.add(getString(R.string.order))
                },
                onNavigateProductClick = {
                    screens.add(getString(R.string.products_details))
                }
            )

            getString(R.string.menu) -> MenuListScreen(
                productModels = sampleProducts,
                onNavigateToDetails = {}
            )

            getString(R.string.drinks_and_cocktails) -> DrinksListScreen(
                productModels = sampleProducts
            )

            getString(R.string.products_details) -> ProductDetailsScreen(
                productModel = sampleProducts.random()
            )

            getString(R.string.order) -> CheckoutScreen(productModels = sampleProducts)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RavelineApp(
    bottomAppBarItemSelected: BottomAppBarItem = bottomAppBarItems.first(),
    onBottomAppBarItemSelectedChange: (BottomAppBarItem) -> Unit = {},
    onFabClick: () -> Unit = {},
    isShowTopBar: Boolean = false,
    isShowBottomBar: Boolean = false,
    isShowFab: Boolean = false,
    isProductDetailSelected: Boolean = false,
    onNavigateBack: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            if (isShowTopBar) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Ristorante Raveline")
                    },
                )
            }
            if (isProductDetailSelected) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Back")
                    },
                    Modifier.background(Color.Transparent),
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                onNavigateBack()
                            })
                    }
                )
            }

        },
        bottomBar = {
            if (isShowBottomBar) {
                RavelineBottomAppBar(
                    item = bottomAppBarItemSelected,
                    items = bottomAppBarItems,
                    onItemChange = onBottomAppBarItemSelectedChange,
                )
            }
        },
        floatingActionButton = {
            if (isShowFab) {
                FloatingActionButton(
                    onClick = onFabClick
                ) {
                    Icon(
                        Icons.Filled.PointOfSale,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun RavelineAppPreview() {
    RavelineTheme {
        Surface {
            RavelineApp {}
        }
    }
}