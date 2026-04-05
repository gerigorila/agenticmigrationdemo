package com.gerigorila.agenticmigrationdemo.presentation.home;

public interface HomeContract {

    interface View {
        void navigateToProductList();
    }

    interface Presenter {
        void onStartClicked();
    }
}
