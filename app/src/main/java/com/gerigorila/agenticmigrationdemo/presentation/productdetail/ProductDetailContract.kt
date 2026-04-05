package com.gerigorila.agenticmigrationdemo.presentation.productdetail

import com.gerigorila.agenticmigrationdemo.domain.model.Product

interface ProductDetailContract {

    interface View {
        fun showProduct(product: Product)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadProduct(productId: Int)
    }
}
