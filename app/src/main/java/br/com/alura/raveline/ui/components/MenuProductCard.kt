package br.com.alura.raveline.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.raveline.R
import br.com.alura.raveline.model.ProductModel
import br.com.alura.raveline.sampledata.sampleProductWithImageModel
import br.com.alura.raveline.sampledata.sampleProductWithoutImageModel
import br.com.alura.raveline.ui.theme.RavelineTheme
import coil.compose.AsyncImage
import java.text.DecimalFormat

@Composable
fun MenuProductCard(
    productModel: ProductModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row {
            Column(
                Modifier
                    .padding(16.dp)
                    .weight(3f)
            ) {
                val decimalFormat = DecimalFormat("0.00")
                Text(
                    text = productModel.name,
                    Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "$${decimalFormat.format(productModel.price)}",
                    Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight(400)
                )
            }
            productModel.image?.let { image ->
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    Modifier
                        .width(80.dp)
                        .fillMaxHeight(),
                    placeholder = painterResource(id = R.drawable.placeholder),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview
@Composable
fun MenuProductCardPreview() {
    RavelineTheme {
        MenuProductCard(
            productModel = sampleProductWithoutImageModel
        )
    }
}

@Preview
@Composable
fun MenuProductCardWithImagePreview() {
    RavelineTheme {
        MenuProductCard(
            productModel = sampleProductWithImageModel
        )
    }
}