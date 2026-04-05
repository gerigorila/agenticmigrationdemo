package com.gerigorila.agenticmigrationdemo.presentation.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gerigorila.agenticmigrationdemo.R;
import com.gerigorila.agenticmigrationdemo.presentation.productlist.ProductListFragment;

public class HomeFragment extends Fragment implements HomeContract.View {

    private HomePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new HomePresenter(this);

        Button btnStart = view.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(v -> presenter.onStartClicked());
    }

    @Override
    public void navigateToProductList() {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ProductListFragment())
                .addToBackStack(null)
                .commit();
    }
}
