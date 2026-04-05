package com.gerigorila.agenticmigrationdemo.presentation.home;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void onStartClicked() {
        view.navigateToProductList();
    }
}
