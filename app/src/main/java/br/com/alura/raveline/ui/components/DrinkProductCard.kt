package br.com.alura.raveline.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.raveline.model.ProductModel
import br.com.alura.raveline.sampledata.sampleProductWithImageModel
import br.com.alura.raveline.sampledata.sampleProductWithoutImageModel
import br.com.alura.raveline.ui.theme.RavelineTheme
import coil.compose.AsyncImage

@Composable
fun DrinkProductCard(
    productModel: ProductModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .width(180.dp)
            .heightIn(
                min = 100.dp
            ),
        shape = RoundedCornerShape(12.dp)
    ) {
        productModel.image?.let { image ->
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = productModel.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = productModel.price.toPlainString(), maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun DrinkProductCardPreview() {
    RavelineTheme {
        DrinkProductCard(
            productModel = sampleProductWithoutImageModel
        )
    }
}

@Preview
@Composable
private fun DrinkProductCardWithImagePreview() {
    RavelineTheme {
        DrinkProductCard(
            productModel = sampleProductWithImageModel
        )
    }
}