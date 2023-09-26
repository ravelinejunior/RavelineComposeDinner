package br.com.alura.raveline.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alura.raveline.R
import br.com.alura.raveline.model.ProductModel
import br.com.alura.raveline.sampledata.sampleProductWithImageModel
import br.com.alura.raveline.sampledata.sampleProductWithoutImageModel
import br.com.alura.raveline.ui.theme.CheckoutCircleButton
import br.com.alura.raveline.ui.theme.RavelineTheme
import coil.compose.AsyncImage

@Composable
fun CheckoutItemCard(
    productModel: ProductModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .height(120.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                Modifier
                    .weight(9f)
                    .fillMaxHeight()
            ) {
                productModel.image?.let { image ->
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        Modifier.width(80.dp),
                        placeholder = painterResource(
                            id = R.drawable.placeholder
                        ),
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
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = productModel.price.toPlainString())
                }
            }
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var quantity by remember {
                    mutableStateOf(1)
                }
                val circleButtonModifier = Modifier
                    .size(20.dp)
                    .background(
                        CheckoutCircleButton,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                Box(
                    circleButtonModifier
                        .clickable { quantity++ }
                ) {
                    Icon(
                        Icons.Filled.ArrowDropUp,
                        contentDescription = null
                    )
                }
                Text(text = "$quantity")
                Box(
                    circleButtonModifier
                        .clickable {
                            if (quantity > 1) {
                                quantity--
                            }
                        }
                ) {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CheckoutItemCardPreview() {
    RavelineTheme {
        CheckoutItemCard(
            productModel = sampleProductWithoutImageModel
        )
    }
}

@Preview
@Composable
private fun CheckoutItemCardWithImagePreview() {
    RavelineTheme {
        CheckoutItemCard(
            productModel = sampleProductWithImageModel
        )
    }
}