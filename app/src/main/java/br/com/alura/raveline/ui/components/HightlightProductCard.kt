package br.com.alura.raveline.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.raveline.R
import br.com.alura.raveline.model.ProductModel
import br.com.alura.raveline.sampledata.sampleProductWithImageModel
import br.com.alura.raveline.sampledata.sampleProductWithoutImageModel
import br.com.alura.raveline.ui.theme.RavelineTheme
import coil.compose.AsyncImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HighlightProductCard(
    productModel: ProductModel,
    modifier: Modifier = Modifier,
    onOrderClick: () -> Unit = {}
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    val bigImage =
        if (expanded) 360.dp
        else 200.dp

    val scaleType =
        if (expanded) ContentScale.Crop
        else ContentScale.FillBounds

    Card(
        modifier
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
    ) {
        Column(Modifier.fillMaxWidth()) {
            productModel.image?.let { image ->
                AsyncImage(
                    image,
                    contentDescription = null,
                    Modifier
                        .fillMaxWidth()
                        .height(bigImage)
                        .combinedClickable(
                            onLongClick = {
                                expanded = !expanded
                            },
                            onLongClickLabel = "Expand",
                        ) {},
                    placeholder = painterResource(id = R.drawable.placeholder),
                    contentScale = scaleType,
                )
            }
            Column(
                Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            ) {
                Text(text = productModel.name)
                Text(text = productModel.price.toString())
                Spacer(Modifier.height(16.dp))
                Text(
                    text = productModel.description,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(18.dp))
            Button(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 24.dp
                    )
                    .clickable {
                        onOrderClick()
                    }
                    .align(Alignment.End),
                onClick = {
                    onOrderClick()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.order),
                )
            }
        }
    }
}

@Preview
@Composable
private fun HighlightProductPreview() {
    RavelineTheme {
        HighlightProductCard(
            productModel = sampleProductWithoutImageModel
        )
    }
}

@Preview
@Composable
private fun HighlightProductCardWithImagePreview() {
    RavelineTheme {
        HighlightProductCard(
            productModel = sampleProductWithImageModel
        )
    }
}