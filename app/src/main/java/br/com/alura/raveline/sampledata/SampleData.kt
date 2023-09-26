package br.com.alura.raveline.sampledata

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.LocalBar
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import br.com.alura.raveline.model.ProductModel
import br.com.alura.raveline.routes.drinksRoute
import br.com.alura.raveline.routes.highLightsRoute
import br.com.alura.raveline.routes.homeRoute
import br.com.alura.raveline.routes.menuRoute
import br.com.alura.raveline.ui.components.BottomAppBarItem
import java.math.BigDecimal
import kotlin.random.Random

private val loremName = LoremIpsum(Random.nextInt(10)).values.first()
private val loremDesc = LoremIpsum(Random.nextInt(30)).values.first()
const val perfectUrl: String =
    "https://thechive.com/wp-content/uploads/2023/09/6b0974ab-49f0-434e-aaed-ff219e6a0b71.jpg?attachment_cache_bust=4492400&quality=85&strip=info"

val sampleProductWithImageModel = ProductModel(
    name = LoremIpsum(10).values.first(),
    price = BigDecimal("9.99"),
    description = LoremIpsum(30).values.first(),
    image = perfectUrl
)

val sampleProductWithoutImageModel = ProductModel(
    name = LoremIpsum(10).values.first(),
    price = BigDecimal("9.99"),
    description = LoremIpsum(30).values.first(),
)

val sampleProductModels = List(10) { index ->
    ProductModel(
        name = loremName,
        price = BigDecimal("9.99"),
        description = loremDesc,
        image = if (index % 2 == 0) perfectUrl else null
    )
}

val bottomAppBarItems = listOf(
    BottomAppBarItem(
        label = "Trends",
        icon = Icons.Filled.AutoAwesome,
        route = highLightsRoute
    ),
    BottomAppBarItem(
        label = "Menu",
        icon = Icons.Filled.RestaurantMenu,
        route = menuRoute
    ),
    BottomAppBarItem(
        label = "Drinks",
        icon = Icons.Outlined.LocalBar,
        route = drinksRoute
    ),
)


val sampleCandies = listOf(
    ProductModel(
        name = "Chocolate",
        price = BigDecimal("3.99"),
        image = "https://images.pexels.com/photos/65882/chocolate-dark-coffee-confiserie-65882.jpeg",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "Ice Cream",
        price = BigDecimal("5.99"),
        image = "https://images.pexels.com/photos/1352278/pexels-photo-1352278.jpeg",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "Cake",
        price = BigDecimal("11.99"),
        image = "https://images.pexels.com/photos/291528/pexels-photo-291528.jpeg",
        description = LoremIpsum(30).values.last()
    )
)

val sampleWomen = listOf(
    ProductModel(
        name = "Chocolate",
        price = BigDecimal("3.99"),
        image = "https://img.freepik.com/premium-photo/portrait-beautiful-sexy-girl_942478-5314.jpg?w=360",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "Ice Cream",
        price = BigDecimal("5.99"),
        image = "https://www.pornocarioca.com/wp-content/uploads/2023/03/photo1677537321-1-1.jpeg",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "Ice Cream",
        price = BigDecimal("5.99"),
        image = "https://www.pornocarioca.com/wp-content/uploads/2021/03/aline-caminhoneira-irritada-com-comentarios-com-sua-pata-de-camelo-13.jpg",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "Ice Cream",
        price = BigDecimal("5.99"),
        image = "https://www.pornocarioca.com/wp-content/uploads/2019/07/cosplay-brasileira-pelada-1.jpeg",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "Salam",
        price = BigDecimal("7.99"),
        image = "https://thechive.com/wp-content/uploads/2023/09/23f53299-473b-4ac3-9b31-96ba27c32c89.jpg?attachment_cache_bust=4495882&quality=85&strip=info",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "Salam",
        price = BigDecimal("7.99"),
        image = "https://thechive.com/wp-content/uploads/2023/09/68dfe2c2-b0f8-4022-b6e9-fb0e88131fe7.jpg?attachment_cache_bust=4495885&quality=85&strip=info",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "Salam",
        price = BigDecimal("7.99"),
        image = "https://thechive.com/wp-content/uploads/2023/08/48926840181b08247c468a7ac485fd6e.jpg?attachment_cache_bust=4472442&quality=85",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "Salam",
        price = BigDecimal("7.99"),
        image = "https://thechive.com/wp-content/uploads/2023/06/36485d4ab85cd964cd39e45eef48a94c.jpeg?attachment_cache_bust=4430824&quality=85",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "Salam",
        price = BigDecimal("7.99"),
        image = "https://thechive.com/wp-content/uploads/2023/09/leilaluuu_370196301_1343804136243765_2729896719972194106_n.jpg?attachment_cache_bust=4492422&quality=85&strip=info",
        description = LoremIpsum(30).values.last()
    ),
)

val sampleDrinks = listOf(
    ProductModel(
        name = "Beer",
        price = BigDecimal("5.99"),
        image = "https://images.pexels.com/photos/1552630/pexels-photo-1552630.jpeg",
        description = LoremIpsum(100).values.first()
    ),
    ProductModel(
        name = "Soda",
        price = BigDecimal("4.99"),
        image = "https://images.pexels.com/photos/2775860/pexels-photo-2775860.jpeg",
        description = loremDesc
    ),
    ProductModel(
        name = "Juice",
        price = BigDecimal("7.99"),
        image = "https://images.pexels.com/photos/96974/pexels-photo-96974.jpeg",
        description = LoremIpsum(30).values.last(),
    ),
    ProductModel(
        name = "Water",
        price = BigDecimal("2.99"),
        image = "https://images.pexels.com/photos/327090/pexels-photo-327090.jpeg",
        description = loremDesc
    )
)

val sampleProducts: List<ProductModel> = listOf(
    ProductModel(
        name = "Hamburguer",
        price = BigDecimal("12.99"),
        image = "https://images.pexels.com/photos/1639557/pexels-photo-1639557.jpeg",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "Pizza",
        price = BigDecimal("19.99"),
        description = loremDesc,
        image = "https://images.pexels.com/photos/825661/pexels-photo-825661.jpeg"
    ),
    ProductModel(
        name = "French Fries",
        price = BigDecimal("7.99"),
        image = "https://images.pexels.com/photos/1583884/pexels-photo-1583884.jpeg",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "French Fries",
        price = BigDecimal("7.99"),
        image = "https://thechive.com/wp-content/uploads/2023/08/7f70299ede44ff3cc2775080ae1a615a.jpg?attachment_cache_bust=4480518&quality=85&strip=info",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "Fries",
        price = BigDecimal("7.99"),
        image = "https://thechive.com/wp-content/uploads/2023/09/6b0974ab-49f0-434e-aaed-ff219e6a0b71.jpg?attachment_cache_bust=4492400&quality=85&strip=info",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "French Fries",
        price = BigDecimal("7.99"),
        image = "https://thechive.com/wp-content/uploads/2023/08/74rtn2lxhbib1.jpg?attachment_cache_bust=4480502&quality=85&strip=info",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "French Fries",
        price = BigDecimal("7.99"),
        image = "https://images.unsplash.com/photo-1574539602047-548bf9557352?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1965&q=80",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "French Fries",
        price = BigDecimal("7.99"),
        image = "https://images.unsplash.com/photo-1515161318750-781d6122e367?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1952&q=80",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "French Fries",
        price = BigDecimal("7.99"),
        image = "https://images.unsplash.com/photo-1557244056-ac3033d17d9a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1974&q=80",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "French Fries",
        price = BigDecimal("7.99"),
        image = "https://images.unsplash.com/photo-1583900985737-6d0495555783?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1964&q=80",
        description = LoremIpsum(30).values.last()
    ),
    ProductModel(
        name = "French Alada",
        price = BigDecimal("7.99"),
        image = "https://thechive.com/wp-content/uploads/2023/09/6b0974ab-49f0-434e-aaed-ff219e6a0b71.jpg?attachment_cache_bust=4492400&quality=85&strip=info",
        description = LoremIpsum(30).values.last()
    ),
    *sampleDrinks.toTypedArray(), *sampleCandies.toTypedArray(), *sampleWomen.toTypedArray()
)


