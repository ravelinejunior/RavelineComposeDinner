package br.com.alura.raveline

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.alura.raveline.navigation.AppDestination
import br.com.alura.raveline.navigation.RavelineNavHost
import br.com.alura.raveline.navigation.bottomAppBarItems
import br.com.alura.raveline.sampledata.sampleDrinks
import br.com.alura.raveline.sampledata.sampleProducts
import br.com.alura.raveline.sampledata.sampleWomen
import br.com.alura.raveline.ui.components.BottomAppBarItem
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

                    val isProductSelected = currentDestination?.let {
                        it.route?.contains(
                            AppDestination.ProductDetailsRoute.route,
                            ignoreCase = true
                        )
                    } ?: false

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
                        Text(text = "")
                    },
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