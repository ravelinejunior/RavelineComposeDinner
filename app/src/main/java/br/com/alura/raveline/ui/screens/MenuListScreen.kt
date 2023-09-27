package br.com.alura.raveline.ui.screens

import androidx.compose.foundation.layout.*
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
import br.com.alura.raveline.sampledata.sampleProductModels
import br.com.alura.raveline.ui.components.MenuProductCard
import br.com.alura.raveline.ui.theme.RavelineTheme
import br.com.alura.raveline.ui.theme.caveatFont

@Composable
fun MenuListScreen(
    modifier: Modifier = Modifier,
    title: String = "Menu",
    productModels: List<ProductModel> = emptyList(),
) {
    Column(
        modifier.fillMaxSize()
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
                MenuProductCard(
                    productModel = p,
                )
            }
        }
    }
}

@Preview
@Composable
fun MenuListScreenPreview() {
    RavelineTheme {
        Surface {
            MenuListScreen(
                productModels = sampleProductModels
            )
        }
    }
}