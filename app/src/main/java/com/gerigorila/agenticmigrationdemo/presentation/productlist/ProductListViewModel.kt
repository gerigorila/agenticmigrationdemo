package com.gerigorila.agenticmigrationdemo.presentation.productlist

import androidx.lifecycle.ViewModel
import com.gerigorila.agenticmigrationdemo.data.repository.ProductRepository
import com.gerigorila.agenticmigrationdemo.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductListViewModel : ViewModel() {

    private val repository = ProductRepository()
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _products.value = repository.getProducts()
    }
}
