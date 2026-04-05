package com.gerigorila.agenticmigrationdemo.presentation.productlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerigorila.agenticmigrationdemo.R
import com.gerigorila.agenticmigrationdemo.data.repository.ProductRepository
import com.gerigorila.agenticmigrationdemo.domain.model.Product
import com.gerigorila.agenticmigrationdemo.presentation.productdetail.ProductDetailFragment

class ProductListFragment : Fragment(), ProductListContract.View {

    private lateinit var presenter: ProductListPresenter
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_product_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProductAdapter()
        adapter.setOnProductClickListener { productId -> navigateToProductDetail(productId) }

        val recycler = view.findViewById<RecyclerView>(R.id.recycler_products)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        presenter = ProductListPresenter(this, ProductRepository())
        presenter.loadProducts()
    }

    override fun showProducts(products: List<Product>) {
        adapter.setProducts(products)
    }

    override fun navigateToProductDetail(productId: Int) {
        val fragment = ProductDetailFragment.newInstance(productId)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
