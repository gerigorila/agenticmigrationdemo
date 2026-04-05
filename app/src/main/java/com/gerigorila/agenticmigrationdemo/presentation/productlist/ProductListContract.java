package com.gerigorila.agenticmigrationdemo.presentation.productlist;

import com.gerigorila.agenticmigrationdemo.domain.model.Product;

import java.util.List;

public interface ProductListContract {

    interface View {
        void showProducts(List<Product> products);
        void navigateToProductDetail(int productId);
    }

    interface Presenter {
        void loadProducts();
        void onProductClicked(int productId);
    }
}
