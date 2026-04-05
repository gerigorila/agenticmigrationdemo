package com.gerigorila.agenticmigrationdemo.presentation.productlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gerigorila.agenticmigrationdemo.R;
import com.gerigorila.agenticmigrationdemo.data.repository.ProductRepository;
import com.gerigorila.agenticmigrationdemo.domain.model.Product;
import com.gerigorila.agenticmigrationdemo.presentation.productdetail.ProductDetailFragment;

import java.util.List;

public class ProductListFragment extends Fragment implements ProductListContract.View {

    private ProductListPresenter presenter;
    private ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ProductAdapter();
        adapter.setOnProductClickListener(this::navigateToProductDetail);

        RecyclerView recycler = view.findViewById(R.id.recycler_products);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.setAdapter(adapter);

        presenter = new ProductListPresenter(this, new ProductRepository());
        presenter.loadProducts();
    }

    @Override
    public void showProducts(List<Product> products) {
        adapter.setProducts(products);
    }

    @Override
    public void navigateToProductDetail(int productId) {
        ProductDetailFragment fragment = ProductDetailFragment.newInstance(productId);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
