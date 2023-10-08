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
import br.com.alura.raveline.ui.components.FailureScreen
import br.com.alura.raveline.ui.components.LoadingScreen
import br.com.alura.raveline.ui.theme.Purple700
import br.com.alura.raveline.ui.theme.RavelineTheme
import br.com.alura.raveline.ui.uistate.ProductDetailsUiState
import coil.compose.AsyncImage
import java.math.BigDecimal
import java.text.DecimalFormat

@Composable
fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    discount: BigDecimal = BigDecimal.ZERO,
    uiState: ProductDetailsUiState,
    onNavigateToCheckout: () -> Unit = {},
    onRetryProduct: () -> Unit = {},
    onBackStack: () -> Unit = {}
) {

    when (uiState) {

        is ProductDetailsUiState.Loading -> {
            LoadingScreen()
        }

        is ProductDetailsUiState.Failure -> {
            val message = uiState.message
            FailureScreen(
                onRetryProduct = onRetryProduct,
                onBackStack = onBackStack,
                message = message
            )
        }

        is ProductDetailsUiState.Success -> {

            val nProduct = uiState.productModel

            val product = nProduct.copy(
                price = nProduct.price.minus((nProduct.price * discount))
            )

            Column(
                modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth(),
                    placeholder = painterResource(id = R.drawable.placeholder),
                    contentScale = ContentScale.Crop
                )

                Column(
                    Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val decimalFormat = DecimalFormat("0.00")
                    Text("$${decimalFormat.format(product.price)}", fontSize = 18.sp)
                    Text(product.name, fontSize = 24.sp)
                    Text(product.description)
                    Button(
                        onClick = {
                            onNavigateToCheckout()
                        },
                        Modifier
                            .fillMaxWidth()
                            .heightIn(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Purple700)
                    ) {
                        Text(text = stringResource(R.string.order_s), color = Color.White)
                    }
                }
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
                uiState = ProductDetailsUiState.Failure(),
            )
        }
    }
}