package com.gerigorila.agenticmigrationdemo.presentation.productlist;

import com.gerigorila.agenticmigrationdemo.data.repository.ProductRepository;
import com.gerigorila.agenticmigrationdemo.domain.model.Product;

import java.util.List;

public class ProductListPresenter implements ProductListContract.Presenter {

    private final ProductListContract.View view;
    private final ProductRepository repository;

    public ProductListPresenter(ProductListContract.View view, ProductRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadProducts() {
        List<Product> products = repository.getProducts();
        view.showProducts(products);
    }

    @Override
    public void onProductClicked(int productId) {
        view.navigateToProductDetail(productId);
    }
}
