package br.com.alura.raveline.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alura.raveline.R
import br.com.alura.raveline.model.ProductModel
import br.com.alura.raveline.sampledata.sampleProducts
import br.com.alura.raveline.ui.theme.RavelineTheme
import coil.compose.AsyncImage

@Composable
fun ProductDetailsScreen(
    productModel: ProductModel,
    modifier: Modifier = Modifier,
    onNavigateToCheckout: () -> Unit = {}
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        productModel.image?.let {
            AsyncImage(
                model = productModel.image,
                contentDescription = null,
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth(),
                placeholder = painterResource(id = R.drawable.placeholder),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(productModel.name, fontSize = 24.sp)
            Text(productModel.price.toPlainString(), fontSize = 18.sp)
            Text(productModel.description)
            Button(
                onClick = {
                    onNavigateToCheckout()
                },
                Modifier
                    .fillMaxWidth()
                    .heightIn(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
            ) {
                Text(text = stringResource(R.string.order_s), color = Color.White)
            }
        }
    }
}

@Preview
@Composable
fun ProductDetailsScreenPreview() {
    RavelineTheme {
        Surface {
            ProductDetailsScreen(
                productModel = sampleProducts.random(),
            )
        }
    }
}