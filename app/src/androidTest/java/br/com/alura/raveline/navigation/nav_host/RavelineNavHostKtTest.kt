package br.com.alura.raveline.navigation.nav_host

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import br.com.alura.raveline.RavelineApp
import br.com.alura.raveline.navigation.checkoutRoute
import br.com.alura.raveline.navigation.drinksRoute
import br.com.alura.raveline.navigation.menuRoute
import br.com.alura.raveline.navigation.navigateMenuList
import br.com.alura.raveline.navigation.navigateToCheckout
import br.com.alura.raveline.navigation.navigateToDrinksList
import br.com.alura.raveline.navigation.navigateToHighlightList
import br.com.alura.raveline.navigation.navigateToProductDetails
import br.com.alura.raveline.navigation.productDetailsRoute
import br.com.alura.raveline.navigation.productIdArgument
import br.com.alura.raveline.sampledata.sampleProducts
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RavelineNavHostKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    private val promoCode = "promoCode"

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            RavelineApp(navController = navController)
        }

        composeTestRule.onRoot().printToLog("RavelineApp @Before")
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithText("Trending Day")
            .assertIsDisplayed()
    }

    @Test
    fun ravelineNavHost_verifyIfMenuScreenIsDisplayed() {
        composeTestRule.onNodeWithText("Menu")
            .performClick()

        composeTestRule.onAllNodesWithText("Menu")
            .onFirst().assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route

        Assert.assertEquals(route, menuRoute)
    }

    @Test
    fun ravelineNavHost_verifyIfDrinksScreenIsDisplayed() {
        composeTestRule.onNodeWithText("Drinks")
            .performClick()

        composeTestRule.onAllNodesWithText(drinksRoute)
            .assertCountEquals(1)

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(route, drinksRoute)
    }

    @Test
    fun ravelineNavHost_verifyIfProductDetailsScreenIsDisplayedFromHighlightsListScreen() {

        composeTestRule
            .onAllNodesWithContentDescription("Highlights Products Card Item")
            .onFirst()
            .performClick()

        composeTestRule.waitUntil(2500) {
            composeTestRule.onAllNodesWithText("Product is not available.")
                .fetchSemanticsNodes().size == 1
        }

        composeTestRule.onNodeWithText("Product is not available.")
            .assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(
            route,
            "$productDetailsRoute/{$productIdArgument}?promoCode={$promoCode}"
        )
    }

    @Test
    fun ravelineNavHost_verifyIfProductDetailsScreenIsDisplayedFromMenuScreen() {
        composeTestRule.onNodeWithText(menuRoute)
            .performClick()

        composeTestRule
            .onAllNodesWithContentDescription("Menu Products Card Item", ignoreCase = true)
            .onFirst()
            .performClick()

        composeTestRule.waitUntil(2500) {
            composeTestRule.onAllNodesWithContentDescription(
                "ProductDetailsUiState.Success Content",
                ignoreCase = true
            ).fetchSemanticsNodes().size == 1
        }

        composeTestRule.onNodeWithContentDescription(
            "ProductDetailsUiState.Success Content",
            ignoreCase = true
        ).assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(
            route,
            "$productDetailsRoute/{$productIdArgument}?promoCode={$promoCode}"
        )
    }

    @Test
    fun ravelineNavHost_verifyIfProductDetailsScreenIsDisplayedFromDiskScreen() {
        composeTestRule.onNodeWithText(drinksRoute)
            .performClick()

        composeTestRule
            .onAllNodesWithContentDescription("Drinks Products Card Item", ignoreCase = true)
            .onFirst()
            .performClick()

        composeTestRule.waitUntil(2500) {
            composeTestRule.onAllNodesWithContentDescription(
                "ProductDetailsUiState.Success Content",
                ignoreCase = true
            ).fetchSemanticsNodes().size == 1
        }

        composeTestRule.onNodeWithContentDescription(
            "ProductDetailsUiState.Success Content",
            ignoreCase = true
        ).assertIsDisplayed()


        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(
            route,
            "$productDetailsRoute/{$productIdArgument}?promoCode={$promoCode}"
        )
    }

    @Test
    fun ravelineNavHost_VerifyIfCheckoutScreenIsDisplayedFromHighLightScreen() {
        composeTestRule.onAllNodesWithText("Order").onFirst().performClick()

        composeTestRule.onAllNodesWithText("Order").onFirst().assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(
            route,
            checkoutRoute
        )
    }

    @Test
    fun ravelineNavHost_VerifyIfCheckoutScreenIsDisplayedFromMenuScreen() {
        composeTestRule.onNodeWithText("Menu").performClick()

        composeTestRule.onNodeWithContentDescription("FloatingActionButton Content MainActivity")
            .performClick()

        composeTestRule.onAllNodesWithText("Order").onFirst().performClick()

        composeTestRule.onAllNodesWithText("Order").onFirst().assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(
            route,
            checkoutRoute
        )
    }

    @Test
    fun ravelineNavHost_VerifyIfCheckoutScreenIsDisplayedFromDrinksScreen() {
        composeTestRule.onNodeWithText("Drinks").performClick()

        composeTestRule.onNodeWithContentDescription("FloatingActionButton Content MainActivity")
            .performClick()

        composeTestRule.onAllNodesWithText("Order").onFirst().performClick()

        composeTestRule.onAllNodesWithText("Order").onFirst().assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(
            route,
            checkoutRoute
        )
    }

    @Test
    fun ravelineNavHost_VerifyIfCheckoutScreenIsDisplayedFromProductDetailsScreen() {
        composeTestRule.runOnUiThread {
            navController.navigateToProductDetails(sampleProducts.first().id, promoCode = promoCode)
        }

        composeTestRule.waitUntil(2500) {
            composeTestRule.onAllNodesWithText("Order")
                .fetchSemanticsNodes().size == 1
        }

        composeTestRule.onAllNodesWithText("Order").onLast() //fix
            .performClick()

        composeTestRule.onAllNodesWithText("Order").onFirst()
            .assertIsDisplayed()

        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(
            route,
            checkoutRoute
        )
    }

    @Test
    fun ravelineNavHost_verifyIfSnackBarIsDisplayedWhenFinishTheOrder() {

        composeTestRule.runOnUiThread {
            navController.navigateToCheckout()
        }

        composeTestRule.onAllNodesWithText("Order").onLast().performClick()

        composeTestRule.onNodeWithTag("RavelineSnackBar")
            .assertIsDisplayed()

    }

    @Test
    fun ravelineNavHost_verifyIfBottomAppBarIsDisplayedOnlyInHomeGraphNavigation() {
        val bottomAppBarTag = "RavelineBottomAppBar"

        composeTestRule.runOnUiThread {
            navController.navigateToHighlightList()
        }
        composeTestRule.onNodeWithTag(bottomAppBarTag)
            .assertIsDisplayed()

        composeTestRule.runOnUiThread {
            navController.navigateMenuList()
        }
        composeTestRule.onNodeWithTag(bottomAppBarTag)
            .assertIsDisplayed()

        composeTestRule.runOnUiThread {
            navController.navigateToDrinksList()
        }
        composeTestRule.onNodeWithTag(bottomAppBarTag)
            .assertIsDisplayed()

        composeTestRule.runOnUiThread {
            navController.navigateToProductDetails(sampleProducts.random().id)
        }
        composeTestRule.onNodeWithTag(bottomAppBarTag)
            .assertDoesNotExist()

        composeTestRule.runOnUiThread {
            navController.navigateToCheckout()
        }
        composeTestRule.onNodeWithTag(bottomAppBarTag)
            .assertDoesNotExist()
    }

    @Test
    fun appNavHost_verifyIfFabIsDisplayedOnlyInMenuOrDrinksDestination() {

        val fabContentDescription = "FloatingActionButton Content MainActivity"

        val assertThatFabIsDisplayed = fun ComposeTestRule.() {
            onNodeWithContentDescription(fabContentDescription)
                .assertIsDisplayed()
        }
        composeTestRule.runOnUiThread {
            navController.navigateMenuList()
        }
        composeTestRule.assertThatFabIsDisplayed()

        composeTestRule.runOnUiThread {
            navController.navigateToDrinksList()
        }
        composeTestRule.assertThatFabIsDisplayed()

        val assertThatFabDoesNotExist = fun ComposeTestRule.() {
            onNodeWithContentDescription(fabContentDescription)
                .assertDoesNotExist()
        }
        composeTestRule.runOnUiThread {
            navController.navigateToHighlightList()
        }
        composeTestRule.assertThatFabDoesNotExist()

        composeTestRule.runOnUiThread {
            navController.navigateToProductDetails(sampleProducts.random().id)
        }
        composeTestRule.assertThatFabDoesNotExist()

        composeTestRule.runOnUiThread {
            navController.navigateToCheckout()
        }
        composeTestRule.assertThatFabDoesNotExist()
    }

    /* Nessa versão já aplico as técnicas para simplificar o código mesmo sendo grande */
    @Test
    fun appNavHost_verifyIfTopAppBarIsDisplayedOnlyInHomeGraphNavigation() =
        with(composeTestRule) {
            val topAppBarTag = "RavelineTopAppBar"

            val assertThatTopAppBarIsDisplayed = fun ComposeTestRule.() {
                onNodeWithTag(topAppBarTag)
                    .assertIsDisplayed()
            }

            runOnUiThread {
                navController.navigateToHighlightList()
            }
            assertThatTopAppBarIsDisplayed()

            runOnUiThread {
                navController.navigateMenuList()
            }
            assertThatTopAppBarIsDisplayed()

            runOnUiThread {
                navController.navigateToDrinksList()
            }
            assertThatTopAppBarIsDisplayed()

            val assertThatTopAppBarDoesNotExist = fun ComposeTestRule.() {
                onNodeWithTag(topAppBarTag)
                    .assertDoesNotExist()
            }
            runOnUiThread {
                navController.navigateToProductDetails(sampleProducts.random().id)
            }
            assertThatTopAppBarDoesNotExist()

            runOnUiThread {
                navController.navigateToCheckout()
            }
            assertThatTopAppBarDoesNotExist()
        }

}
