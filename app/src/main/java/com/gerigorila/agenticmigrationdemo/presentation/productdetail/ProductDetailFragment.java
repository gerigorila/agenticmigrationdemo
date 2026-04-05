package com.gerigorila.agenticmigrationdemo.presentation.productdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gerigorila.agenticmigrationdemo.R;
import com.gerigorila.agenticmigrationdemo.data.repository.ProductRepository;
import com.gerigorila.agenticmigrationdemo.domain.model.Product;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Locale;

public class ProductDetailFragment extends Fragment implements ProductDetailContract.View {

    private static final String ARG_PRODUCT_ID = "product_id";

    private ProductDetailPresenter presenter;

    public static ProductDetailFragment newInstance(int productId) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(com.google.android.material.R.drawable.ic_arrow_back_black_24);
        toolbar.setNavigationOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());

        presenter = new ProductDetailPresenter(this, new ProductRepository());

        int productId = requireArguments().getInt(ARG_PRODUCT_ID);
        presenter.loadProduct(productId);
    }

    @Override
    public void showProduct(Product product) {
        View view = getView();
        if (view == null) return;

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(product.getName());

        TextView txtName = view.findViewById(R.id.txt_name);
        TextView txtPrice = view.findViewById(R.id.txt_price);
        TextView txtDescription = view.findViewById(R.id.txt_description);

        txtName.setText(product.getName());
        txtPrice.setText(String.format(Locale.US, "$%.2f", product.getPrice()));
        txtDescription.setText(product.getDescription());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
