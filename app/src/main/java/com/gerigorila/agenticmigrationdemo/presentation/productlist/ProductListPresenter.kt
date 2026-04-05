package com.gerigorila.agenticmigrationdemo.presentation.productlist

import com.gerigorila.agenticmigrationdemo.data.repository.ProductRepository

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val repository: ProductRepository
) : ProductListContract.Presenter {

    override fun loadProducts() {
        val products = repository.getProducts()
        view.showProducts(products)
    }

    override fun onProductClicked(productId: Int) {
        view.navigateToProductDetail(productId)
    }
}
