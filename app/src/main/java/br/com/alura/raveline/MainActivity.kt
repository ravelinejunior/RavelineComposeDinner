package br.com.alura.raveline

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.alura.raveline.navigation.BottomAppBarItem
import br.com.alura.raveline.navigation.bottomAppBarItems
import br.com.alura.raveline.navigation.checkoutRoute
import br.com.alura.raveline.navigation.drinksRoute
import br.com.alura.raveline.navigation.graph.navigateSingleTopWithPopUpTo
import br.com.alura.raveline.navigation.highLightsRoute
import br.com.alura.raveline.navigation.menuRoute
import br.com.alura.raveline.navigation.nav_host.RavelineNavHost
import br.com.alura.raveline.navigation.nav_host.TAG
import br.com.alura.raveline.navigation.productDetailsRoute
import br.com.alura.raveline.ui.components.RavelineBottomAppBar
import br.com.alura.raveline.ui.screens.*
import br.com.alura.raveline.ui.theme.RavelineTheme
import br.com.alura.raveline.utils.orderDoneKey
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val TAG: String? = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            RavelineTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RavelineApp()
                }
            }
        }
    }


}

@Composable
fun RavelineApp(
    navController: NavHostController = rememberNavController()
) {
    // See the change in navigation
    LaunchedEffect(Unit) {
        navController.addOnDestinationChangedListener { _, _, _ ->
            val routes = navController.backQueue.map {
                it.destination.route
            }
            Log.i("MainActivityTAG", "onCreate: back stack - $routes")
        }
    }

    //Saved state
    val backStackEntryState by navController.currentBackStackEntryAsState()
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    val orderMessageDone = backStackEntryState
        ?.savedStateHandle
        ?.getStateFlow<String?>(orderDoneKey, null)
        ?.collectAsState()

    orderMessageDone?.value?.let { message ->
        scope.launch {
            snackBarHostState.showSnackbar(message = message)
        }
    }

    Log.i(TAG, "Order done message: ${orderMessageDone?.value}")


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
            navController.navigateSingleTopWithPopUpTo(item)
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
        },
        snackBarHostState = snackBarHostState,
    ) {
        RavelineNavHost(navController = navController)
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
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    content: @Composable () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { snackData ->
                Snackbar(
                    modifier = Modifier
                        .padding(12.dp)
                        .statusBarsPadding()
                ) {
                    Text(
                        text = snackData.visuals.message,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
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
            RavelineApp(content = {})
        }
    }
}
/*

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
            onNavigateToDetails = {

            }
        )

        getString(R.string.drinks_and_cocktails) -> DrinksListScreen(
            productModels = sampleProducts
        )

        getString(R.string.products_details) -> ProductDetailsScreen(
            productModel = sampleProducts.random()
        )

        getString(R.string.order) -> CheckoutScreen(productModels = sampleProducts)
    }
}*/

