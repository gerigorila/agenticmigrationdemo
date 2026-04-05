package com.gerigorila.agenticmigrationdemo.presentation.productdetail;

import com.gerigorila.agenticmigrationdemo.domain.model.Product;

public interface ProductDetailContract {

    interface View {
        void showProduct(Product product);
        void showError(String message);
    }

    interface Presenter {
        void loadProduct(int productId);
    }
}
