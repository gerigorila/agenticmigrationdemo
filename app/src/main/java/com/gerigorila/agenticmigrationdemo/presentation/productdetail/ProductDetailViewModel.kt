package com.gerigorila.agenticmigrationdemo.presentation.productdetail

import androidx.lifecycle.ViewModel
import com.gerigorila.agenticmigrationdemo.data.repository.ProductRepository
import com.gerigorila.agenticmigrationdemo.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductDetailViewModel : ViewModel() {

    private val repository = ProductRepository()
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product.asStateFlow()

    fun loadProduct(productId: Int) {
        _product.value = repository.getProductById(productId)
    }
}
