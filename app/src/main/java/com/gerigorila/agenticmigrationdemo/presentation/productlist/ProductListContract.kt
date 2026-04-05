package com.gerigorila.agenticmigrationdemo.presentation.productlist

import com.gerigorila.agenticmigrationdemo.domain.model.Product

interface ProductListContract {

    interface View {
        fun showProducts(products: List<Product>)
        fun navigateToProductDetail(productId: Int)
    }

    interface Presenter {
        fun loadProducts()
        fun onProductClicked(productId: Int)
    }
}
