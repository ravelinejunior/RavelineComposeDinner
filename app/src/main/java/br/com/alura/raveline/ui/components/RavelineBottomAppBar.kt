package br.com.alura.raveline.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import br.com.alura.raveline.sampledata.bottomAppBarItems
import br.com.alura.raveline.ui.theme.RavelineTheme

class BottomAppBarItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

@Composable
fun RavelineBottomAppBar(
    item: BottomAppBarItem,
    modifier: Modifier = Modifier,
    items: List<BottomAppBarItem> = emptyList(),
    onItemChange: (BottomAppBarItem) -> Unit = {}
) {
    NavigationBar(modifier) {
        items.forEach {
            val label = it.label
            val icon = it.icon
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                selected = item.label == label,
                onClick = {
                    onItemChange(it)
                }
            )
        }
    }
}

@Preview
@Composable
fun RavelineBottomAppBarPreview() {
    RavelineTheme {
        RavelineBottomAppBar(
            item = bottomAppBarItems.first(),
            items = bottomAppBarItems
        )
    }
}