package com.gerigorila.agenticmigrationdemo.presentation.home

interface HomeContract {
    interface View {
        fun navigateToProductList()
    }

    interface Presenter {
        fun onStartClicked()
    }
}
