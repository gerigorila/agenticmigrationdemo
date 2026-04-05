package com.gerigorila.agenticmigrationdemo.presentation.productlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerigorila.agenticmigrationdemo.R
import com.gerigorila.agenticmigrationdemo.presentation.productdetail.ProductDetailFragment
import kotlinx.coroutines.launch

class ProductListFragment : Fragment() {

    private lateinit var adapter: ProductAdapter
    private val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(this)[ProductListViewModel::class.java]
    }

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collect { products ->
                    adapter.setProducts(products)
                }
            }
        }
    }

    private fun navigateToProductDetail(productId: Int) {
        val fragment = ProductDetailFragment.newInstance(productId)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
