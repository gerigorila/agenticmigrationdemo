package com.gerigorila.agenticmigrationdemo.presentation.productlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gerigorila.agenticmigrationdemo.domain.model.Product
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onProductClick: (Int) -> Unit,
    viewModel: ProductListViewModel = viewModel()
) {
    val products by viewModel.products.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Products") }) }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.padding(padding)
        ) {
            items(products) { product ->
                ProductCard(product = product, onClick = { onProductClick(product.id) })
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color(0xFFE0E0E0))
            )
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(product.name, fontWeight = FontWeight.Bold)
                Text(
                    text = String.format(Locale.US, "$%.2f", product.price),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
