package br.com.alura.raveline.navigation.nav_host

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
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
import br.com.alura.raveline.navigation.productDetailsRoute
import br.com.alura.raveline.navigation.productIdArgument
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
}
