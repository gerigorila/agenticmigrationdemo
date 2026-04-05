package com.gerigorila.agenticmigrationdemo.presentation.productdetail

import com.gerigorila.agenticmigrationdemo.data.repository.ProductRepository

class ProductDetailPresenter(
    private val view: ProductDetailContract.View,
    private val repository: ProductRepository
) : ProductDetailContract.Presenter {

    override fun loadProduct(productId: Int) {
        val product = repository.getProductById(productId)
        if (product != null) {
            view.showProduct(product)
        } else {
            view.showError("Product not found")
        }
    }
}
