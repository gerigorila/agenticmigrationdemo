package com.gerigorila.agenticmigrationdemo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.gerigorila.agenticmigrationdemo.R
import com.gerigorila.agenticmigrationdemo.presentation.productlist.ProductListFragment

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var presenter: HomePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = HomePresenter(this)

        view.findViewById<Button>(R.id.btn_start).setOnClickListener {
            presenter.onStartClicked()
        }
    }

    override fun navigateToProductList() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, ProductListFragment())
            .addToBackStack(null)
            .commit()
    }
}
