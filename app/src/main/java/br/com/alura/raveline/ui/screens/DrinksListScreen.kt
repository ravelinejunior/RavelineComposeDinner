package br.com.alura.raveline.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.raveline.model.ProductModel
import br.com.alura.raveline.sampledata.sampleProducts
import br.com.alura.raveline.ui.components.DrinkProductCard
import br.com.alura.raveline.ui.theme.RavelineTheme
import br.com.alura.raveline.ui.theme.caveatFont
import br.com.alura.raveline.ui.uistate.DrinksListUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DrinksListScreen(
    modifier: Modifier = Modifier,
    title: String = "Drinks And Cocktails",
    uiState: DrinksListUiState = DrinksListUiState(),
    columns: Int = 2,
    onProductClick: (ProductModel) -> Unit = {},
) {

    val products = uiState.products

    Column(
        modifier
            .fillMaxSize()
    ) {
        Surface {
            Text(
                text = title,
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                fontFamily = caveatFont,
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
        }
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(columns),
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp
        ) {
            items(products) { p ->
                DrinkProductCard(
                    productModel = p,
                    modifier = Modifier.clickable {
                        onProductClick(p)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DrinksListScreenPreview() {
    RavelineTheme {
        Surface {
            DrinksListScreen(
                title = "Drinks",
                uiState = DrinksListUiState(products = sampleProducts),
                onProductClick = {}
            )
        }
    }
}