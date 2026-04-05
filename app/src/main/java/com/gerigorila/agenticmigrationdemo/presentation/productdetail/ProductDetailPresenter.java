package com.gerigorila.agenticmigrationdemo.presentation.productdetail;

import com.gerigorila.agenticmigrationdemo.data.repository.ProductRepository;
import com.gerigorila.agenticmigrationdemo.domain.model.Product;

public class ProductDetailPresenter implements ProductDetailContract.Presenter {

    private final ProductDetailContract.View view;
    private final ProductRepository repository;

    public ProductDetailPresenter(ProductDetailContract.View view, ProductRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadProduct(int productId) {
        Product product = repository.getProductById(productId);
        if (product != null) {
            view.showProduct(product);
        } else {
            view.showError("Product not found");
        }
    }
}
