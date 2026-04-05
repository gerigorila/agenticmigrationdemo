package com.gerigorila.agenticmigrationdemo.presentation.home

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {

    override fun onStartClicked() {
        view.navigateToProductList()
    }
}
