package br.com.alura.raveline

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.alura.raveline.navigation.AppDestination
import br.com.alura.raveline.navigation.bottomAppBarItems
import br.com.alura.raveline.navigation.promoCodeParam
import br.com.alura.raveline.sampledata.sampleDrinks
import br.com.alura.raveline.sampledata.sampleProducts
import br.com.alura.raveline.sampledata.sampleWomen
import br.com.alura.raveline.ui.components.BottomAppBarItem
import br.com.alura.raveline.ui.components.RavelineBottomAppBar
import br.com.alura.raveline.ui.screens.*
import br.com.alura.raveline.ui.theme.RavelineTheme
import java.math.BigDecimal

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

                    val selectedItem by remember(currentDestination) {
                        val item = currentDestination?.let { destination ->
                            bottomAppBarItems.find {
                                it.destination.route == destination.route
                            }
                        } ?: bottomAppBarItems.first()
                        mutableStateOf(item)
                    }

                    //Verify if bottom app bar has items
                    val containsInBottomAppBarItems = currentDestination?.let { navDestination ->
                        bottomAppBarItems.find {
                            it.destination.route == navDestination.route
                        }
                    } != null

                    val isShowFab = when (currentDestination?.route) {
                        AppDestination.MenuRoute.route,
                        AppDestination.DrinksRoute.route -> true

                        else -> false
                    }

                    RavelineApp(
                        bottomAppBarItemSelected = selectedItem,
                        onBottomAppBarItemSelectedChange = {
                            val route = it.destination.route
                            navController.navigate(route = route) {
                                launchSingleTop = true
                                popUpTo(route)
                            }
                        },
                        onFabClick = {
                            //Navigate to checkout
                            navController.navigate(AppDestination.CheckoutRoute.route)
                        },
                        isShowBottomBar = containsInBottomAppBarItems,
                        isShowTopBar = containsInBottomAppBarItems,
                        isShowFab = isShowFab
                    ) {

                        NavHost(
                            navController = navController,
                            startDestination = AppDestination.TrendsHighlightRoute.route,
                        ) {
                            composable(AppDestination.TrendsHighlightRoute.route) {
                                HighlightsListScreen(
                                    productModels = sampleProducts.sortedBy {
                                        it.name
                                    },
                                    onNavigateProductClick = { product ->
                                        val promoCode = "Fif"
                                        navController.navigate("${AppDestination.ProductDetailsRoute.route}/${product.id}")
                                    },
                                    onNavigateOrderClick = {
                                        navController.navigate(AppDestination.CheckoutRoute.route)
                                    }
                                )
                            }
                            composable(AppDestination.MenuRoute.route) {
                                MenuListScreen(
                                    productModels = sampleProducts + sampleWomen.shuffled(),
                                    onNavigateToDetails = { product ->
                                        val promoCode = "Banana"
                                        navController.navigate("${AppDestination.ProductDetailsRoute.route}/${product.id}?promoCode=$promoCode")
                                    }
                                )
                            }
                            composable(AppDestination.DrinksRoute.route) {
                                DrinksListScreen(
                                    productModels = sampleWomen + sampleDrinks,
                                    onNavigateToDetails = { product ->
                                        val promoCode = "Coit"
                                        navController.navigate("${AppDestination.ProductDetailsRoute.route}/${product.id}?promoCode=$promoCode")
                                    }
                                )
                            }
                            composable(
                                "${AppDestination.ProductDetailsRoute.route}/{productId}?promoCode={$promoCodeParam}",
                                arguments = listOf(navArgument(promoCodeParam) {
                                    nullable = true
                                })
                            ) { backStackEntry ->

                                val id = backStackEntry.arguments?.getString("productId")

                                //Get selected product
                                val selectedProduct = sampleProducts.firstOrNull { productModel ->
                                    productModel.id == id
                                }

                                Log.i(TAG, "Product selected: $selectedProduct")

                                //Discount Product
                                val promoCode = backStackEntry.arguments?.getString(promoCodeParam)
                                sampleProducts.find {
                                    it.id == id
                                }?.let { productModel ->

                                    val discount = when (promoCode) {
                                        "Coit" -> BigDecimal(0.1)
                                        "Banana" -> BigDecimal(0.2)
                                        "Fif" -> BigDecimal(0.5)
                                        else -> BigDecimal.ZERO
                                    }

                                    val currentPrice = productModel.price

                                    ProductDetailsScreen(
                                        productModel = productModel.copy(price = currentPrice - (currentPrice * discount)),
                                        onNavigateToCheckout = {
                                            navController.navigate(AppDestination.CheckoutRoute.route)
                                        }
                                    )
                                } ?: LaunchedEffect(Unit) {
                                    navController.navigateUp()
                                }

                            }
                            composable(AppDestination.CheckoutRoute.route) {
                                CheckoutScreen(
                                    productModels = sampleWomen,
                                    onPopBackStack = {
                                        navController.navigateUp()
                                    },
                                )
                            }
                        }
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
        Log.i("MainActivity", "onCreate: screens ${screens.toList()}")
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
                productModels = sampleDrinks + sampleWomen
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