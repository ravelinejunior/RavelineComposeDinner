package br.com.alura.raveline.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import br.com.alura.raveline.ui.components.HighlightProductCard
import br.com.alura.raveline.ui.theme.RavelineTheme
import br.com.alura.raveline.ui.theme.caveatFont
import br.com.alura.raveline.ui.uistate.HighlightsListUiState

@Composable
fun HighlightsListScreen(
    modifier: Modifier = Modifier,
    title: String = "Trending Day",
    uiState: HighlightsListUiState = HighlightsListUiState(),
    onNavigateProductClick: (ProductModel) -> Unit = {},
    onOrderClick: () -> Unit = {}
) {

    val productModels = uiState.products

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
        LazyColumn(
            modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(productModels) { p ->
                HighlightProductCard(
                    productModel = p,
                    Modifier.clickable {
                        onNavigateProductClick(p)
                    },
                    onOrderClick = {
                        onOrderClick()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun HighlightsListScreenPreview() {
    RavelineTheme {
        Surface {
            HighlightsListScreen(
                title = "Trending Day",
                uiState = HighlightsListUiState(products = sampleProducts),
                onNavigateProductClick = { },
                onOrderClick = {}
            )
        }
    }
}